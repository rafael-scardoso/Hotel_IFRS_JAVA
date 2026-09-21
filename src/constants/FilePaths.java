package constants;

import java.nio.file.Path;

public class FilePaths {

    public static final Path DB_DIR=Path.of("db");
    public static final Path DB_FILE = DB_DIR.resolve("database.db");
    /*public static final Path DB_MATERIAL=DB_DIR.resolve("db_material.csv");
    public static final Path DB_PART=DB_DIR.resolve("db_part.csv");
    public static final Path DB_PRODUCT=DB_DIR.resolve("db_product.csv");
    public static final Path DB_PACKAGE=DB_DIR.resolve("db_package.csv");
    public static final Path DB_ORDER=DB_DIR.resolve("db_order.csv");*/

}
