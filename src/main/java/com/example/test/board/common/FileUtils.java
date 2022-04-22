package com.example.test.board.common;

import com.example.test.board.entity.BoardFileDto;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@Component
public class FileUtils {

    public List<BoardFileDto> parseFileInfo(long boardIdx, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        if(ObjectUtils.isEmpty(multipartHttpServletRequest)) {
            return null;
        }

        List<BoardFileDto> fileList = new ArrayList<>();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        ZonedDateTime current = ZonedDateTime.now();
        String path = "images/" + current.format(format);
        File file = new File(path);
        if(file.exists() == false) {
            file.mkdirs();
        }

        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();

        String newFileName, originalFileExtension, contentType;

        while(iterator.hasNext()) {
            List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());
            for(MultipartFile multipartFile : list) {
                if(multipartFile.isEmpty() == false) {
                    contentType = multipartFile.getContentType();
                    if(ObjectUtils.isEmpty(contentType)) {
                        break;
                    }
                    else {
                        if(contentType.contains("image/jpeg")) {
                            originalFileExtension = ".jpg";
                        }
                        else if(contentType.contains("image/png")) {
                            originalFileExtension = ".png";
                        }
                        else if(contentType.contains("image/gif")) {
                            originalFileExtension = ".gif";
                        }
                        else if (contentType.contains("application/pdf")){
                            originalFileExtension = ".pdf";
                        }
                        else if (contentType.contains("text/plain")){
                            originalFileExtension = ".txt";
                        }
//                        else if (contentType.contains("application/vnd.ms-excel")){
//                            originalFileExtension = ".xls";
//                        }
//                        else if (contentType.contains("application/vnd.openxmlformats-officedocument.presentationml.presentation")){
//                            originalFileExtension = ".pptx";
//                        }
//                        else if (contentType.contains("application/vnd.openxmlformats-officedocument.wordprocessingml.document")){
//                            originalFileExtension = ".docx";
//                        }
                        else {
                            String[] type = multipartFile.getOriginalFilename().split("[.]");
                            originalFileExtension = "."+type[type.length-1];
                        }
                    }

                    newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
                    BoardFileDto boardFile = new BoardFileDto();
                    boardFile.setBoardIdx(boardIdx);
                    boardFile.setFileSize(multipartFile.getSize());
                    boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
                    boardFile.setStoredFilePath(path + "/" + newFileName);
                    fileList.add(boardFile);

                    file = new File(path + "/" + newFileName);
                    multipartFile.transferTo(file);
                }
            }
        }
        return fileList;
    }
}