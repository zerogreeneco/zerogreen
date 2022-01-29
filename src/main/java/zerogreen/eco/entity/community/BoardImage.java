package zerogreen.eco.entity.community;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerogreen.eco.entity.baseentity.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_image_id")
    private Long id;

    private String uploadFileName;
    private String storeFileName;
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "board_id")
    private CommunityBoard board;

    // 파일 폴더 저장 (FileService)
    public BoardImage(String uploadFileName, String storeFileName, String filePath) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.filePath = filePath;
    }

    // 파일 DB 저장
    public BoardImage(String uploadFileName, String storeFileName, String filePath, CommunityBoard board) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.filePath = filePath;
        this.board = board;
    }
}
