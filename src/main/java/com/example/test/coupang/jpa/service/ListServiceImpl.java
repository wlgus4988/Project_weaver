package com.example.test.coupang.jpa.service;

import com.example.test.board.common.FileUtils;
import com.example.test.board.jpa.entity.BoardFileDto;
import com.example.test.coupang.jpa.entity.FileEntity;
import com.example.test.coupang.jpa.entity.ListEntity;
import com.example.test.coupang.jpa.repository.CoupangFileRepository;
import com.example.test.coupang.jpa.repository.ListRepository;
import com.example.test.coupang.jpa.repository.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

//@Service
public class ListServiceImpl implements ListService {

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    CoupangFileRepository filerepository;

    @Autowired
    ListRepository listrepo;

    @Autowired
    OptionRepository optionRepo;

    @Override
    public void productSave(ListEntity list, FileEntity files, MultipartFile singleFile, List<MultipartFile> multiFiles ) throws Exception {

        List<BoardFileDto> multiFileList = fileUtils.parseMultiFileInfo(list.getProductIdx(), multiFiles);
        if (CollectionUtils.isEmpty(multiFileList) == false) {
            for (int i = 0; i < multiFileList.size(); i++) {
                files.setIdx(filerepository.seq());
                files.setProductIdx(listrepo.save(list).getProductIdx());
                files.setCreatorId(list.getUsername());

                files.setFileSize(multiFileList.get(i).getFileSize());
                files.setOriginalFileName(multiFileList.get(i).getOriginalFileName());
                files.setStoredFilePath(multiFileList.get(i).getStoredFilePath());
                System.out.println("================>>>>>" + files.toString());
                filerepository.save(files);
            }
        }
        if (singleFile.isEmpty() == false) {
            BoardFileDto file = fileUtils.parseSingleFileInfo(list.getProductIdx(), singleFile);
            files.setIdx(filerepository.seq());
            files.setProductIdx(listrepo.save(list).getProductIdx());
            files.setCreatorId(list.getUsername());

            files.setFileSize(file.getFileSize());
            files.setOriginalFileName(file.getOriginalFileName());
            files.setStoredFilePath(file.getStoredFilePath());
            files.setMainImage("Y");
            filerepository.save(files);
            list.setImageSrc(files.getStoredFilePath());
        }

        listrepo.save(list);

    }
}
