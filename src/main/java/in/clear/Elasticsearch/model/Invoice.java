package in.clear.Elasticsearch.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    private String invoiceId;
    private DocumentDetails documentDetails;
    private InvoiceData invoiceData;
    private InvoiceStatus status;
    private String createdAt;
    private String invoiceLabel;

}
