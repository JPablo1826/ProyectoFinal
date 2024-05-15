package co.edu.uniquindio.poo.model;

public enum TipoLocalidad {
    GENERAL("General"), VIP("VIP");

    private String nombre;

    public static TipoLocalidad getTipoLocalidad(String nombre) {
        for (TipoLocalidad tipoLocalidad : TipoLocalidad.values()) {
            if (tipoLocalidad.nombre.equals(nombre)) {
                return tipoLocalidad;
            }
        }
        return null;
    }
    public static String[] getValueStrings(){
        var values = TipoLocalidad.values();
        String[] strings = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            strings[i] = values[i].getNombre();
        }
        return strings;
    }
    private TipoLocalidad(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
