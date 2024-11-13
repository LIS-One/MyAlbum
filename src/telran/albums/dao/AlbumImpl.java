package telran.albums.dao;

import telran.albums.model.Photo;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

public class AlbumImpl implements Album {
    private Photo[] photos;
    private int size;
    Comparator<Photo> comparator = (p1,p2)->{
        return p1.getDate().toLocalDate().compareTo(p2.getDate().toLocalDate());
    };

    public AlbumImpl(int capacity) {
        photos = new Photo[capacity];
    }

    @Override
    public boolean addPhoto(Photo photo) {
        if (photo == null || size == photos.length||getPhotoFromAlbum(photo.getPhotoId(),photo.getAlbumId())!=null) {
            return false;
        }
        int index = Arrays.binarySearch(photos, 0, size, photo, comparator);
        if (index < 0) {
            index = -index - 1;
        }


        System.arraycopy(photos, index, photos, index + 1, size - index);
        photos[index] = photo;
        size++;

        return true;
    }
    @Override
    public boolean removePhoto(int photoId, int albumId) {
        for (int i = 0; i <size ; i++) {
            if(photos[i].getPhotoId()==photoId&&photos[i].getAlbumId()==albumId){
                System.arraycopy(photos, i + 1, photos, i, size - i - 1);
                photos[--size] = null;
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean updatePhoto(int photoId, int albumId, String url) {
        for (int i = 0; i < photos.length; i++) {
            if(photos[i].getPhotoId()==photoId&&photos[i].getAlbumId()==albumId){
                photos[i].setUrl(url);
                return true;
            }

        }
        return false;
    }

    @Override
    public int size() {
      return size;
    }

    @Override
    public Photo getPhotoFromAlbum(int photoId, int albumId) {
        for (int i = 0; i < size; i++) {
            if (photos[i].getAlbumId() == albumId && photos[i].getPhotoId() == photoId) {
                return photos[i];
            }
        }
        return null;
    }

    @Override
    public Photo[] getAllPhotoFromAlbum(int albumId) {
        Predicate<Photo> predicate = (p->p.getAlbumId()==albumId);
        int match = 0;
        for (int i = 0; i <size; i++) {
            if(predicate.test(photos[i])){
                match++;
            }

        }
        Photo[] albumPhotos = new Photo[match];
        for (int i = 0,j=0; i <size; i++) {
            if(predicate.test(photos[i])){
                albumPhotos[j]=photos[i];
                j++;
            }

        }
        return albumPhotos;
    }

    @Override
    public Photo[] getPhotoBetweenDate(LocalDate date, LocalDate dateTo) {
        Comparator<Object> dateComparator= (p1,d2)-> {
                return ((Photo) p1).getDate().toLocalDate().compareTo((LocalDate) d2);
        };
        int startIndex = Arrays.binarySearch(photos,0,size,date,dateComparator);
        int endIndex = Arrays.binarySearch(photos,0,size,dateTo,dateComparator);
        int left =startIndex;
        int right = endIndex;
        while(left>0&&!photos[left-1].getDate().toLocalDate().isBefore(date)){
            left--;
        }
        while(right<size&&!photos[right].getDate().toLocalDate().isAfter(dateTo)){
            right++;
        }
        return Arrays.copyOfRange(photos,left,right-1);

}}
