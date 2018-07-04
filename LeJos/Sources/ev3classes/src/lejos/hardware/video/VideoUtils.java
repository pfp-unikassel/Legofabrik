package lejos.hardware.video;

public class VideoUtils
{
    public static int fourcc(char a, char b, char c, char d)
    {
        return (a & 0xff) | ((b & 0xff) << 8) | ((c & 0xff) << 16) | ((d & 0xff) << 24);
    }
}
