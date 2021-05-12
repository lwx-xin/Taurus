package org.taurus.extendEntity;

import org.taurus.entity.SFileEntity;

public class SFileEntityEx extends SFileEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 文件的请求路径
     * /f/xxx/xxxx/x.txt
     */
    private String fileUrl;

    /**
     * 文件封面图请求路径
     */
    private String fileCoverUrl;

    public String getFileUrl() {
        return fileUrl;
    }

    public String getFileCoverUrl() {
        return fileCoverUrl;
    }

    public void setFileCoverUrl(String fileCoverUrl) {
        this.fileCoverUrl = fileCoverUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
