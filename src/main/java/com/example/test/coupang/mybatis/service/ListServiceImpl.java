package com.example.test.coupang.mybatis.service;

import com.example.test.board.common.FileUtils;
import com.example.test.board.jpa.entity.BoardFileDto;
import com.example.test.coupang.jpa.entity.FileEntity;
import com.example.test.coupang.jpa.entity.ListEntity;
import com.example.test.coupang.jpa.repository.CoupangFileRepository;
import com.example.test.coupang.jpa.repository.ListRepository;
import com.example.test.coupang.jpa.repository.OptionRepository;
import com.example.test.coupang.mybatis.dto.CoupangFileDto;
import com.example.test.coupang.mybatis.dto.ListDto;
import com.example.test.coupang.mybatis.mapper.CoupangFileMapper;
import com.example.test.coupang.mybatis.mapper.ListMapper;
import com.example.test.coupang.mybatis.mapper.OptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ListServiceImpl implements ListService {

    @Autowired
    private FileUtils fileUtils;

   /* @Autowired
    CoupangFileRepository filerepository;*/

    @Autowired
    CoupangFileMapper coupangfilemapper;

    @Autowired
    ListMapper listmapper;

    @Autowired
    OptionMapper optionmapper;

    /*@Autowired
    OptionRepository optionRepo;*/

    @Override
    public void productSave(ListDto list, CoupangFileDto files, MultipartFile singleFile, List<MultipartFile> multiFiles) throws Exception {
        long productIdx = listmapper.seq();
        list.setProductIdx(productIdx);
        System.out.println(productIdx);
        List<BoardFileDto> multiFileList = fileUtils.parseMultiFileInfo(productIdx, multiFiles);
        if (CollectionUtils.isEmpty(multiFileList) == false) {
            for (int i = 0; i < multiFileList.size(); i++) {
                files.setIdx(coupangfilemapper.seq());
                files.setProductIdx(productIdx);
                files.setCreatorId(list.getUsername());

                files.setFileSize(multiFileList.get(i).getFileSize());
                files.setOriginalFileName(multiFileList.get(i).getOriginalFileName());
                files.setStoredFilePath(multiFileList.get(i).getStoredFilePath());
                System.out.println("================>>>>>" + files.toString());
                coupangfilemapper.saveFile(files);
            }
        }
        if (singleFile.isEmpty() == false) {
            BoardFileDto file = fileUtils.parseSingleFileInfo(list.getProductIdx(), singleFile);
            files.setIdx(coupangfilemapper.seq());
            files.setProductIdx(productIdx);
            files.setCreatorId(list.getUsername());

            files.setFileSize(file.getFileSize());
            files.setOriginalFileName(file.getOriginalFileName());
            files.setStoredFilePath(file.getStoredFilePath());
            files.setMainImage("Y");
            coupangfilemapper.saveFile(files);
            list.setImageSrc(files.getStoredFilePath());
        }

        if(list.getProductType() == null){
            list.setProductType("-");
        }
        if(list.getOptionColor() == null){
            list.setProductType("-");
        }
        if(list.getOptionSize() == null){
            list.setProductType("-");
        }

        listmapper.productSave(list);

    }

    @Override
    public List<ListDto> findAll(String searchText, long idx, Long proCateNum) throws Exception {
        System.out.println("=====================================>서비스단 들어옴"+ proCateNum);
        return listmapper.findAll(searchText, idx, proCateNum);
    }

    @Override
    public ListDto findById(long productIdx) throws Exception {
        return listmapper.findById(productIdx);
    }

    @Override
    public void deleteById(long productIdx) throws Exception {
        listmapper.deleteById(productIdx);
    }

    @Override
    public long seq() throws Exception {
        return listmapper.seq();
    }
}
