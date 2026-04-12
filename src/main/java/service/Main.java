package service;

import factory.DaoFactory;
import ui.UI;

public class Main {

    public static void main(String[] args) {

        DaoFactory.init();

        UI.printTitle("INICIO DA EXECUÇÃO");

    }
}