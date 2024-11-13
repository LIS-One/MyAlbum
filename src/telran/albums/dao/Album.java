package telran.albums.dao;

import telran.albums.model.Photo;

import java.time.LocalDate;

public interface Album {
    boolean addPhoto(Photo photo);
    boolean removePhoto(int photoId, int albumId);
    boolean updatePhoto(int photoId, int albumId, String url);
    Photo getPhotoFromAlbum(int photoId, int albumId);
    Photo[] getAllPhotoFromAlbum(int albumId);
    Photo[] getPhotoBetweenDate(LocalDate date, LocalDate dateTo);
    int size();
}
