package co.edu.uniquindio.poo;

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

    private TipoLocalidad(String nombre) {
        this.nombre = nombre;
    }
}
