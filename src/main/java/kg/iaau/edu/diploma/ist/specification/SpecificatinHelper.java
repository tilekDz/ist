package kg.iaau.edu.diploma.ist.specification;

public class SpecificatinHelper {
    public static String getContainsLike(String searchTerm){
        return searchTerm == null || searchTerm.isEmpty() ? "%" : "%" + searchTerm.toLowerCase() + "%";
    }
}