package in.clear.Elasticsearch.scripts;


import com.fasterxml.jackson.databind.ObjectMapper;
import in.clear.Elasticsearch.model.*;


import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class jsongenerator {

    public static void main(String[] args) {
        generateNdjsonFile("invoice", "/Users/ketiriakshitha/Desktop/invoice.ndjson", 80000);
    }

    private static void generateNdjsonFile(String indexName, String fileName, int numEntries) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            ObjectMapper objectMapper = new ObjectMapper();
            Random random = new Random();

            for (int i = 1120001; i <= 1120000 + numEntries; i++) {
                Map<String, Object> document = createRandomDocument(i, random);
                Map<String, Object> indexAction = createIndexAction(indexName, i);


                fileWriter.write(objectMapper.writeValueAsString(indexAction));
                fileWriter.write("\n");


                fileWriter.write(objectMapper.writeValueAsString(document));
                fileWriter.write("\n");
            }

            System.out.println("NDJSON file generated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Object> createRandomDocument(int documentNumber, Random random) {
        Map<String, Object> document = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        document.put("createdAt", dateFormat.format(new Date()));
        document.put("documentDetailsCreatedBy", "User" + documentNumber);
        document.put("invoiceDataBarcodeDetails", "Barcode" + documentNumber);
        document.put("invoiceLabel", "LABEL-" + UUID.randomUUID().toString().substring(0, 4));
        InvoiceStatus[] statusValues = InvoiceStatus.values();
        document.put("status", statusValues[random.nextInt(statusValues.length)]);
        document.put("invoiceDataBillToDetailsGstin", generateRandomGstin());
        document.put("invoiceDataBillToDetailsPan", generateRandomPan());
        document.put("invoiceDataBillToDetailsBranchName", generateBranch());
        document.put("invoiceDataVendorDetailsName", generateName());
        document.put("invoiceDataVendorDetailsCode", generateVendorCode());
        document.put("invoiceDataVendorDetailsMsmeClassification", generateMsmeClassification());
        document.put("invoiceDataPoDetailsPoNumber", generatePoDetails());
        document.put("invoiceDataGrnDetailsGrnNumber", generateGrnDetails());
        document.put("invoiceDataInvoiceDocumentDetailsShipmentNumber", generateShipmentNumber());
        document.put("invoiceDataInvoiceDocumentDetailsFiDocumentNumber", generateFiDocumentNumber());
        document.put("invoiceDataInvoiceDocumentDetailsGoodsServiceType", generateGoodsServiceType());
        document.put("invoiceDataInvoiceDocumentDetailsTotalInvoiceAmount", generateTotalInvoiceAmount());
        document.put("invoiceDataInvoiceDocumentDetailsDocumentType", generateDocumentType());
        document.put("invoiceDataInvoiceDocumentDetailsDocumentDate", generateDocumentDate());
        document.put("invoiceDataInvoiceDocumentDetailsSupplierDocumentNumber", generateSupplierDocumentNumber());
        document.put("invoiceId","INV-" + UUID.randomUUID().toString().substring(0, 8));

        return document;
    }

    private static Map<String, Object> createIndexAction(String indexName, int documentNumber) {
        Map<String, Object> indexAction = new HashMap<>();
        indexAction.put("index", Map.of("_index", indexName, "_id", String.valueOf(documentNumber)));
        return indexAction;
    }
    private static String generateBarcode() {
        Random random = new Random();
        BarcodeDetails barcodeDetails = new BarcodeDetails();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int digit = random.nextInt(10);
            stringBuilder.append(digit);
        }
        return "BAR" + stringBuilder.toString();

    }


    private static String generateBranch() {
        Random random = new Random();
        String[] places = {"New York", "London", "Tokyo", "Paris", "Sydney", "Rome", "Cairo", "Rio de Janeiro", "Moscow", "Cape Town", "Dubai", "Toronto", "Singapore", "Berlin", "Beijing", "Los Angeles", "Mumbai", "Stockholm", "Amsterdam", "Seoul", "Barcelona", "Istanbul", "Mexico City", "Bangkok", "Dublin", "Athens", "Vienna", "Prague", "Buenos Aires", "Helsinki"};
        return places[random.nextInt(places.length)];
    }

    private static String generateRandomPan() {
        StringBuilder panBuilder = new StringBuilder();

        // Generate 5 uppercase letters
        for (int i = 0; i < 5; i++) {
            char randomLetter = (char) ('A' + new Random().nextInt(26));
            panBuilder.append(randomLetter);
        }

        // Generate 4 random digits
        for (int i = 0; i < 4; i++) {
            int randomDigit = new Random().nextInt(10);
            panBuilder.append(randomDigit);
        }

        // Generate 1 uppercase letter (usually 'A', 'B', 'C', or 'P')
        char randomLastLetter = "ABCP".charAt(new Random().nextInt(4));
        panBuilder.append(randomLastLetter);

        return panBuilder.toString();
    }
    private static String generateRandomGstin() {
        StringBuilder gstinBuilder = new StringBuilder("27"); // Assuming GSTIN starts with state code 27 for testing

        Random random = new Random();

        // Generate 10 random digits for the GSTIN
        for (int i = 0; i < 10; i++) {
            int randomDigit = random.nextInt(10);
            gstinBuilder.append(randomDigit);
        }

        // Calculate and append the checksum digit
        String gstinWithoutChecksum = gstinBuilder.toString();
        char checksumDigit = calculateGstinChecksum(gstinWithoutChecksum);
        gstinBuilder.append(checksumDigit);

        return gstinBuilder.toString();
    }

    private static char calculateGstinChecksum(String gstinWithoutChecksum) {
        int factor = 2;
        int sum = 0;

        // Calculate weighted sum of digits
        for (int i = gstinWithoutChecksum.length() - 2; i >= 0; i--) {
            int digit = Integer.parseInt(String.valueOf(gstinWithoutChecksum.charAt(i)));
            sum += digit * factor;

            factor = (factor == 1) ? 2 : 1;
        }

        // Calculate and return the checksum digit
        int checksumDigit = (int) ((sum + 10) % 11);
        return (checksumDigit == 0) ? '0' : (char) (11 - checksumDigit + '0');
    }

    private static String generateVendorCode() {
        Random random = new Random();
        StringBuilder vendorCode = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10);
            vendorCode.append(digit);
        }

        return vendorCode.toString();

    }

    private static String generateName() {
        Random random = new Random();
        String[] people = {"Sophia", "Jackson", "Olivia", "Liam", "Emma", "Noah", "Ava", "Lucas", "Isabella", "Oliver",
                "Amelia", "Ethan", "Mia", "Aiden", "Harper", "Caden", "Ella", "Grayson", "Aria", "Muhammad",
                "Scarlett", "Carter", "Chloe", "Matthew", "Abigail", "Mason", "Emily", "Sebastian", "Sofia", "Logan"};
        return people[random.nextInt(people.length)];
    }

    private static MMSE generateMsmeClassification() {
        Random random = new Random();
        MMSE[] mmse = MMSE.values();
        return mmse[random.nextInt(mmse.length)];
    }

    private static String generateGrnDetails() {
        String prefix = "GRN";
        int randomSerialNumber = new Random().nextInt(9000) + 1000;
        return prefix + randomSerialNumber;

    }

    private static String generatePoDetails() {
        int randomNumber = new Random().nextInt(1000000);
        String randomPO = "PO" + randomNumber;
        return randomPO;
    }


    private static String generateShipmentNumber() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int digit = random.nextInt(10);
            stringBuilder.append(digit);
        }

        return "SHIP" + stringBuilder.toString();
    }

    private static String generateFiDocumentNumber() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int digit = random.nextInt(10);
            stringBuilder.append(digit);
        }

        return "FID" + stringBuilder.toString();
    }

    private static String generateSupplierDocumentNumber() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder stringBuilder = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            stringBuilder.append(characters.charAt(index));
        }

        return "SUP" + stringBuilder.toString();
    }

    private static String generateTotalInvoiceAmount() {
        Random random = new Random();
        int firstDigit = random.nextInt(9) + 1;
        int remainingDigits = random.nextInt(9000000) + 1000000;
        int ans  = firstDigit * 10000000 + remainingDigits;
        return String.valueOf(ans);
    }

    private static DocumentType generateDocumentType() {
        Random random = new Random();
        DocumentType[] documentTypes = DocumentType.values();
        return documentTypes[random.nextInt(documentTypes.length)];
    }

    private static GoodsServiceType generateGoodsServiceType() {
        Random random = new Random();
        GoodsServiceType[] goodsServiceType = GoodsServiceType.values();
        return   goodsServiceType[random.nextInt(goodsServiceType.length)];
    }

    private static String generateDocumentDate() {

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = currentDate.format(formatter);

        return formattedDate;
    }

    private static String generateCreatedBy() {

        Random random = new Random();
        String[] people = {"Sophia", "Jackson", "Olivia", "Liam", "Emma", "Noah", "Ava", "Lucas", "Isabella", "Oliver",
                "Amelia", "Ethan", "Mia", "Aiden", "Harper", "Caden", "Ella", "Grayson", "Aria", "Muhammad",
                "Scarlett", "Carter", "Chloe", "Matthew", "Abigail", "Mason", "Emily", "Sebastian", "Sofia", "Logan"};
        return people[random.nextInt(people.length)];

    }
}



