package com.example.test.board.jpa.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import com.example.test.board.jpa.repository.boardRepository;
import com.example.test.board.jpa.repository.fileRepository;
import com.google.gson.JsonObject;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

//@Controller
//@RestController
public class fileController {

    @Autowired
    fileRepository filerepository;

    @Autowired
    boardRepository boardrepository;

    // 게시물 업데이트 시 파일 삭제
    @ResponseBody
    @PostMapping(value = "/DeleteFile")
    public void fileDeleteAjax(@RequestParam("fileIdx") long fileIdx, @RequestParam("boardIdx") long boardIdx) throws Exception {

        filerepository.deleteById(fileIdx);
        boardrepository.deleteFile(boardIdx);

        System.out.println("삭제했어용");
    }

    // 파일 다운로드
    @GetMapping("/download/{idx}")
    public ResponseEntity<Object> download(@PathVariable long idx) {

        String path = "C:/Users/weaver-gram-0014/Desktop/Project/" + filerepository.findByIdx(idx).getStoredFilePath();

        try {
            Path filePath = Paths.get(path);
            Resource resource = new InputStreamResource(Files.newInputStream(filePath)); // 파일 resource 얻기

            //File file = new File(path);

            HttpHeaders headers = new HttpHeaders();
            String fileName = URLEncoder.encode(filerepository.findByIdx(idx).getOriginalFileName(), "UTF-8");
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(fileName).build());  // 다운로드 되거나 로컬에 저장되는 용도로 쓰이는지를 알려주는 헤더

            return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
        }
    }

    // summernote - 게시글에 이미지 첨부
    @PostMapping(value = "/uploadSummernoteImageFile", produces = "application/json; charset=utf8")
    @ResponseBody
    public String uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
        JsonObject jsonObject = new JsonObject();

        // 내부경로로 저장
//        String contextRoot = new HttpServletRequestWrapper(request).getRealPath("/");
//        System.out.println(contextRoot);
//        String fileRoot = contextRoot+"resources/fileupload/";
//        System.out.println(fileRoot);

        // 외부경로 저장
        String fileRoot = "C:\\Users\\weaver-gram-0014\\Desktop\\IMAGE\\";

        String originalFileName = multipartFile.getOriginalFilename();    //오리지날 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));    // 파일 확장자
        String savedFileName = UUID.randomUUID() + extension;    //저장될 파일 명

        File targetFile = new File(fileRoot + savedFileName);
        System.out.println(targetFile);

        try {
            InputStream fileStream = multipartFile.getInputStream();
            FileUtils.copyInputStreamToFile(fileStream, targetFile);    // 파일 저장

            // 내부 경로
//           jsonObject.addProperty("url", "resources/fileupload/"+savedFileName);     // contextroot + resources + 저장할 내부 폴더명
            // 외부 경로
            jsonObject.addProperty("url", "/summernoteImage/" + savedFileName);
            jsonObject.addProperty("responseCode", "success");

        } catch (IOException e) {
            FileUtils.deleteQuietly(targetFile);    //저장된 파일 삭제
            jsonObject.addProperty("responseCode", "error");
            e.printStackTrace();
        }
        String a = jsonObject.toString();
        return a;
    }
}
