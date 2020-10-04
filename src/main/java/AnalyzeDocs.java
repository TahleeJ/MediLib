//import com.google.cloud.language.v1.*;
//
//public class AnalyzeDocs {
//    // Instantiate the Language client com.google.cloud.language.v1.LanguageServiceClient
//try (LanguageServiceClient language = LanguageServiceClient.create()) {
//        // set the GCS content URI path
//        Document doc =
//                Document.newBuilder().setGcsContentUri(gcsUri).setType(Type.PLAIN_TEXT).build();
//        ClassifyTextRequest request = ClassifyTextRequest.newBuilder().setDocument(doc).build();
//        // detect categories in the given file
//        ClassifyTextResponse response = language.classifyText(request);
//
//        for (ClassificationCategory category : response.getCategoriesList()) {
//            System.out.printf(
//                    "Category name : %s, Confidence : %.3f\n",
//                    category.getName(), category.getConfidence());
//        }
//    }
//}
