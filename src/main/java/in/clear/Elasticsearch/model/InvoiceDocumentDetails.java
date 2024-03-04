package in.clear.Elasticsearch.model;





import lombok.Data;

@Data
public class InvoiceDocumentDetails {
    private String supplierDocumentNumber;
    private int documentDate;
    private DocumentType documentType;
    private String totalInvoiceAmount;
    private GoodsServiceType goodsServiceType;
    private String fiDocumentNumber;
    private String shipmentNumber;
}
