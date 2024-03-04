package in.clear.Elasticsearch.model;

import lombok.Data;


@Data
public class Invoice {

    private String invoiceId;
    private DocumentDetails documentDetails;
    private InvoiceData invoiceData;
    private InvoiceStatus status;
    private String createdAt;
    private String invoiceLabel;

}
