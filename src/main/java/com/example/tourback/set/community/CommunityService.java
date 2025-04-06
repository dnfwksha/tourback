package com.example.tourback.set.community;

import com.example.tourback.global.SecurityUtils;
import com.example.tourback.global.s3Service.S3Upload;
import com.example.tourback.set.community.image.CommunityImage;
import com.example.tourback.set.community.image.CommunityImageRepository;
import com.example.tourback.set.community.querydsl.CommunityQueryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.example.tourback.set.community.image.CommunityImage.ImageType.COMMUNITY;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final CommunityImageRepository communityImageRepository;
    private final SecurityUtils securityUtils;
    private final S3Upload s3Upload;

    public void create(CommunityDto communityDto) throws IOException {
        List<MultipartFile> images = new ArrayList<>();
        images.addAll(communityDto.getImages());
        var communityCode = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        Community community = new Community();
        community.setCommunityCode(communityCode);
        community.setTitle(communityDto.getTitle());
        community.setAuthor(securityUtils.getCurrentUsername());
        community.setStatus("Y");
        communityRepository.save(community);

        System.out.println("이미지 개수 : "+images.size());
        if (!images.isEmpty()) {
            for (int i = 0; i < images.size(); i++) {
                MultipartFile image = images.get(i);
                String originalFilename = image.getOriginalFilename();
                String imageFilename = System.currentTimeMillis() + "community_" + originalFilename;
                String imageUrl = s3Upload.uploadFileToS3(image, imageFilename);

                CommunityImage communityImage = new CommunityImage();
                communityImage.setCommunityCode(communityCode);
                communityImage.setImageType(COMMUNITY);
                communityImage.setImageUrl(imageUrl);
                communityImage.setDisplayOrder(i + 1);
                communityImageRepository.save(communityImage);
            }
        }
    }


    public List<CommunityQueryDto> findAllCommunity() {
        return communityRepository.findAllCommunity();
    }

    public CommunityQueryDto findOne(String communityCode) {
        return communityRepository.findOneCommunity(communityCode);
    }
}
