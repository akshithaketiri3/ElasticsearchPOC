package in.clear.Elasticsearch.model;

import lombok.Data;

@Data
public class InvoiceData {
    private InvoiceDocumentDetails invoiceDocumentDetails;
    private BuyerDetails billToDetails;
    private VendorDetails vendorDetails;
    private PODetails poDetails;
    private GRNDetails grnDetails;
    private BarcodeDetails barcodeDetails;
}
