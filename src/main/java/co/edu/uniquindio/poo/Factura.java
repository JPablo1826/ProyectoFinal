package co.edu.uniquindio.poo;
    import java.time.LocalDate;

    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.Builder;
    
    @Data
    @Builder
    @AllArgsConstructor
    public class Factura {
        private Compra compra;
        private double total;
        private LocalDate fechaCompra;
        private String codigoFactura;
        @Override
        public String toString() {
            return "Factura [compra=" + compra + ", total=" + total + ", fechaCompra=" + fechaCompra
                    + ", codigoFactura=" + codigoFactura + "]";
        }
}
