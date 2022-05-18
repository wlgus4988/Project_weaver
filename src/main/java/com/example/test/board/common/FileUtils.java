package com.example.test.board.common;

import com.example.test.board.jpa.entity.BoardFileDto;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class FileUtils {

    public List<BoardFileDto> parseFileInfo(long boardIdx, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        if (ObjectUtils.isEmpty(multipartHttpServletRequest)) {
            return null;
        }

        List<BoardFileDto> fileList = new ArrayList<>();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        ZonedDateTime current = ZonedDateTime.now();
        String path = "src/main/resources/static/img/" + current.format(format);
        File file = new File(path);
        if (file.exists() == false) {
            file.mkdirs();
        }

        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();

        String newFileName, originalFileExtension, contentType;

        while (iterator.hasNext()) {
            List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());
            for (MultipartFile multipartFile : list) {
                if (multipartFile.isEmpty() == false) {
                    contentType = multipartFile.getContentType();
                    if (ObjectUtils.isEmpty(contentType)) {
                        break;
                    } else {
                        if (contentType.contains("image/jpeg")) {
                            originalFileExtension = ".jpg";
                        } else if (contentType.contains("image/png")) {
                            originalFileExtension = ".png";
                        } else if (contentType.contains("image/gif")) {
                            originalFileExtension = ".gif";
                        } else if (contentType.contains("application/pdf")) {
                            originalFileExtension = ".pdf";
                        } else if (contentType.contains("text/plain")) {
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
                            originalFileExtension = "." + type[type.length - 1];
                        }
                    }

                    newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
                    BoardFileDto boardFile = new BoardFileDto();
                    boardFile.setBoardIdx(boardIdx);
                    boardFile.setFileSize(multipartFile.getSize());
                    boardFile.setOriginalFileName(multipartFile.getOriginalFilename());

                    boardFile.setStoredFilePath("../../img/" + current.format(format) + newFileName);
                    fileList.add(boardFile);

                    file = new File(path + "/" + newFileName);
                    multipartFile.transferTo(file);
                }
            }
        }
        return fileList;
    }

    public List<BoardFileDto> parseMultiFileInfo(long boardIdx, List<MultipartFile> multiFiles) throws Exception {
        if (ObjectUtils.isEmpty(multiFiles)) {
            return null;
        }

        List<BoardFileDto> fileList = new ArrayList<>();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        ZonedDateTime current = ZonedDateTime.now();
        String path = "src/main/resources/static/img/" + current.format(format);
        File file = new File(path);
        if (file.exists() == false) {
            file.mkdirs();
        }

//        Iterator<String> iterator = multiFiles.getFileNames();

        String newFileName, originalFileExtension, contentType;
/*
        while(iterator.hasNext()) {
            List<MultipartFile> list = multiFiles.getFiles(iterator.next());*/
        for (MultipartFile multipartFile : multiFiles) {
            if (multipartFile.isEmpty() == false) {
                contentType = multipartFile.getContentType();
                if (ObjectUtils.isEmpty(contentType)) {
                    break;
                } else {
                    if (contentType.contains("image/jpeg")) {
                        originalFileExtension = ".jpg";
                    } else if (contentType.contains("image/png")) {
                        originalFileExtension = ".png";
                    } else if (contentType.contains("image/gif")) {
                        originalFileExtension = ".gif";
                    } else if (contentType.contains("application/pdf")) {
                        originalFileExtension = ".pdf";
                    } else if (contentType.contains("text/plain")) {
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
                        originalFileExtension = "." + type[type.length - 1];
                    }
                }

                newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
                BoardFileDto boardFile = new BoardFileDto();
                boardFile.setBoardIdx(boardIdx);
                boardFile.setFileSize(multipartFile.getSize());
                boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
                boardFile.setStoredFilePath("/img/" + current.format(format) + '/' + newFileName);
                fileList.add(boardFile);

                file = new File(path + "/" + newFileName);
                multipartFile.transferTo(file);
            }
        }
//        }
        return fileList;
    }

    public BoardFileDto parseSingleFileInfo(long boardIdx, MultipartFile multiFiles) throws Exception {
        if (multiFiles.isEmpty()) {
            return null;
        } else {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
            ZonedDateTime current = ZonedDateTime.now();

            HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            HttpSession session = req.getSession();
            String paths = session.getServletContext().getRealPath("/") + current.format(format) ;
            //System.out.println(paths);

            String path = "src/main/resources/static/img/" + current.format(format);
            File file = new File(path);
            if (file.exists() == false) {
                file.mkdirs();
            }

            String newFileName, originalFileExtension, contentType;

            contentType = multiFiles.getContentType();
            if (contentType.contains("image/jpeg")) {
                originalFileExtension = ".jpg";
            } else if (contentType.contains("image/png")) {
                originalFileExtension = ".png";
            } else if (contentType.contains("image/gif")) {
                originalFileExtension = ".gif";
            } else if (contentType.contains("application/pdf")) {
                originalFileExtension = ".pdf";
            } else if (contentType.contains("text/plain")) {
                originalFileExtension = ".txt";
            } else {
                String[] type = multiFiles.getOriginalFilename().split("[.]");
                originalFileExtension = "." + type[type.length - 1];
            }


            newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
            BoardFileDto boardFile = new BoardFileDto();
            boardFile.setBoardIdx(boardIdx);
            boardFile.setFileSize(multiFiles.getSize());
            boardFile.setOriginalFileName(multiFiles.getOriginalFilename());
            boardFile.setStoredFilePath("/img/" + current.format(format) + '/' + newFileName);


            file = new File(path + "/" + newFileName);
            multiFiles.transferTo(file);

            return boardFile;
        }


    }
}