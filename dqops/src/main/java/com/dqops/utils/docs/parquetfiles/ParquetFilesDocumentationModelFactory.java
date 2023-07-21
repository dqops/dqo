package com.dqops.utils.docs.parquetfiles;

import java.util.List;

public interface ParquetFilesDocumentationModelFactory {
    /**
     * Create a parquet file documentation models.
     *
     * @return Parquet file documentation models.
     */
    List<ParquetFileDocumentationModel> createDocumentationForParquetFiles();
}
