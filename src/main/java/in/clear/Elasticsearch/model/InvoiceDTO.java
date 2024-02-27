package in.clear.Elasticsearch.model;

import lombok.Data;

@Data
public class InvoiceDTO {

    private String invoiceId;
    private String documentDetailsCreatedBy;
    private String invoiceDataInvoiceDocumentDetailsSupplierDocumentNumber;
    private String  invoiceDataInvoiceDocumentDetailsDocumentDate;
    private DocumentType invoiceDataInvoiceDocumentDetailsDocumentType;
    private String invoiceDataInvoiceDocumentDetailsTotalInvoiceAmount;
    private GoodsServiceType invoiceDataInvoiceDocumentDetailsGoodsServiceType;
    private String invoiceDataInvoiceDocumentDetailsFiDocumentNumber;
    private String invoiceDataInvoiceDocumentDetailsShipmentNumber;
    private String invoiceDataBillToDetailsGstin;
    private String invoiceDataBillToDetailsPan;
    private String invoiceDataBillToDetailsBranchName;
    private String invoiceDataVendorDetailsName;
    private String invoiceDataVendorDetailsCode;
    private MMSE invoiceDataVendorDetailsMsmeClassification;
    private String invoiceDataPoDetailsPoNumber;
    private String invoiceDataGrnDetailsGrnNumber;
    private String invoiceDataBarcodeDetails;
    private InvoiceStatus status;
    private String createdAt;
    private String invoiceLabel;
    private String invoiceDataBuyerDetailsGstin;
    private String invoiceDataBuyerDetailsPan;
    private String invoiceDataBuyerDetailsBranchName;

}
