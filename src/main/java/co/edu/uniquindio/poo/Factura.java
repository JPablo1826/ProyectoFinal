package co.edu.uniquindio.poo;
    import java.time.LocalDate;

    import lombok.AllArgsConstructor;
    import lombok.Data;
    
    @Data
    @AllArgsConstructor
    public class Factura {
        private int subTotal;
        private int total;
        private LocalDate fechaCompra;
        private String codigoFactura;
        private String codigoQr;
    
    
        public void enviarCorre(){
            
        }
}
