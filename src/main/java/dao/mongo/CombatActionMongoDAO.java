package dao.mongo;

import com.mongodb.MongoException;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import contracts.CombatActionDAO;
import model.CombatAction;
import model.dto.AcaoCombateDTO;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CombatActionMongoDAO implements CombatActionDAO {

    private final MongoCollection<Document> collection;

    public CombatActionMongoDAO(MongoDatabase database) {
        this.collection = database.getCollection("acaocombate");
    }

    @Override
    public Integer insert(CombatAction combatAction) throws SQLException {
        try {
            Integer actionId = nextActionId();
            Document doc = new Document("_id", actionId)
                    .append("id_combate", combatAction.getCombatId())
                    .append("id_tipo_acao_combate", combatAction.getCombatActionTypeId())
                    .append("id_agente", combatAction.getActorId())
                    .append("id_alvo", combatAction.getTargetId())
                    .append("id_item_usado", combatAction.getItemId())
                    .append("id_magia_usada", combatAction.getMagicId())
                    .append("ordem_turno", combatAction.getTurnOrder())
                    .append("valor_resultado", combatAction.getResultValue());

            collection.insertOne(doc);
            return actionId;
        } catch (MongoException e) {
            throw new SQLException("Erro ao inserir acao de combate no MongoDB", e);
        }
    }

    @Override
    public CombatAction findById(Integer combatActionId) throws SQLException {
        try {
            Document doc = collection.find(Filters.eq("_id", combatActionId)).first();
            if (doc == null) return null;
            return fromDocument(doc);
        } catch (MongoException e) {
            throw new SQLException("Erro ao buscar acao de combate por ID no MongoDB", e);
        }
    }

    @Override
    public List<CombatAction> listByCombatId(Integer combatId) throws SQLException {
        List<CombatAction> actions = new ArrayList<>();
        try {
            Bson filter = Filters.eq("id_combate", combatId);
            Bson sort = Sorts.ascending("ordem_turno", "_id");
            for (Document doc : collection.find(filter).sort(sort)) {
                actions.add(fromDocument(doc));
            }
        } catch (MongoException e) {
            throw new SQLException("Erro ao listar acoes de combate no MongoDB", e);
        }
        return actions;
    }

    @Override
    public List<AcaoCombateDTO> listReportByActorIdAndType(Integer actorId, Integer actionTypeId) throws SQLException {
        List<AcaoCombateDTO> reportList = new ArrayList<>();
        try {
            List<Bson> pipeline = new ArrayList<>();

            Bson matchStage;
            if (actionTypeId != null && actionTypeId != 0) {
                matchStage = Aggregates.match(Filters.and(
                        Filters.eq("id_agente", actorId),
                        Filters.eq("id_tipo_acao_combate", actionTypeId)
                ));
            } else {
                matchStage = Aggregates.match(Filters.eq("id_agente", actorId));
            }
            pipeline.add(matchStage);

            pipeline.add(Aggregates.lookup("tipoacaocombate", "id_tipo_acao_combate", "_id", "tac_info"));
            pipeline.add(Aggregates.lookup("personagem", "id_alvo", "_id", "p_alvo_info"));
            pipeline.add(Aggregates.lookup("itens", "id_item_usado", "_id", "item_info"));
            pipeline.add(Aggregates.lookup("magias", "id_magia_usada", "_id", "magia_info"));

            pipeline.add(Aggregates.sort(Sorts.ascending("id_combate", "ordem_turno")));

            AggregateIterable<Document> result = collection.aggregate(pipeline);

            for (Document doc : result) {
                String nomeAcaoCombate = extractFirstString(doc, "tac_info", "nome_acao_combate");
                String nomeAlvo = extractFirstString(doc, "p_alvo_info", "nome_personagem");
                String nomeItem = extractFirstString(doc, "item_info", "nome_item");
                String nomeMagia = extractFirstString(doc, "magia_info", "nome_magia");

                reportList.add(new AcaoCombateDTO(
                        doc.getInteger("id_combate"),
                        doc.getInteger("ordem_turno"),
                        nomeAcaoCombate,
                        nomeAlvo,
                        nomeItem,
                        nomeMagia,
                        doc.getInteger("valor_resultado")
                ));
            }
        } catch (MongoException e) {
            throw new SQLException("Erro ao gerar relatorio de acoes no MongoDB", e);
        }
        return reportList;
    }

    private Integer nextActionId() {
        Document lastAction = collection.find().sort(Sorts.descending("_id")).first();
        if (lastAction == null || lastAction.getInteger("_id") == null) return 1;
        return lastAction.getInteger("_id") + 1;
    }

    private CombatAction fromDocument(Document doc) {
        Integer id = doc.getInteger("_id");
        Integer combatId = doc.getInteger("id_combate");
        Integer combatActionTypeId = doc.getInteger("id_tipo_acao_combate");
        Integer actorId = doc.getInteger("id_agente");
        Integer targetId = doc.getInteger("id_alvo");
        Integer itemId = doc.getInteger("id_item_usado");
        Integer magicId = doc.getInteger("id_magia_usada");
        Integer turnOrder = doc.getInteger("ordem_turno");
        Integer resultValue = doc.getInteger("valor_resultado");

       return new CombatAction(
                id,
                combatId,
                combatActionTypeId,
                actorId,
                targetId,
                itemId,
                magicId,
                turnOrder,
                resultValue
        );
    }

    private String extractFirstString(Document doc, String arrayField, String targetField) {
        List<Document> list = doc.getList(arrayField, Document.class);
        if (list != null && !list.isEmpty()) {
            return list.get(0).getString(targetField);
        }
        return null;
    }
}