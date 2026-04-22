package model.relationship;

import model.Magic;

public class KnownMagic {
    private Integer sheetId;
    private Integer magicId;
    private Magic magic;

    private KnownMagic(Integer sheetId, Integer magicId){this(sheetId, magicId, null);}

    public KnownMagic(Integer sheetId, Integer magicId, Magic magic) {
        this.sheetId = sheetId;
        this.magicId = magicId;
        this.magic = magic;
    }

    public Integer getSheetId() {
        return sheetId;
    }

    public Integer getMagicId() {
        return magicId;
    }

    public Magic getMagic() {
        return magic;
    }
}
