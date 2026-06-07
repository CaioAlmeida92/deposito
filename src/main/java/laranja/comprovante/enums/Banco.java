package laranja.comprovante.enums;

public enum Banco {
    LARANJA,
    ROXO,
    VERMELHO,
    AZUL;

    public static boolean contains(String value){
        if(value == null){
            return false;
        }
        for(Banco banco : Banco.values()){
            if(banco.name().equalsIgnoreCase(value)){
                return true;
            }
        }
        return false;
    }
}
