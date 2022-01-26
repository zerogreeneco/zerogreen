package zerogreen.eco.entity.file;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerogreen.eco.entity.userentity.StoreMember;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoreImageFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String fileName;
    private String storeFileName;
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private StoreMember storeMember;

    public StoreImageFile(String fileName, String storeFileName, String filePath) {
        this.fileName = fileName;
        this.storeFileName = storeFileName;
        this.filePath = filePath;
    }

    public StoreImageFile(String fileName, String storeFileName, String filePath, StoreMember storeMember) {
        this.fileName = fileName;
        this.storeFileName = storeFileName;
        this.filePath = filePath;
        this.storeMember = storeMember;
    }
}
