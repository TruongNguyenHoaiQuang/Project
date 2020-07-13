package com.example.demo_projectd.model;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.Random;
import java.util.Vector;

import static org.opencv.core.Core.*;
import static org.opencv.core.CvType.CV_32FC1;
import static org.opencv.core.CvType.CV_8UC1;
import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.imgproc.Imgproc.*;


public class ObstacleDetection {
    public static String _path;

    public static class Vec4f {
        public double dpX;
        public double dpY;
        public double pX;
        public double pY;


        public void addVec4f(double x1, double y1, double x2, double y2) {
            this.dpX = x1;
            this.dpY = y1;
            this.pX = x2;
            this.pY = y2;
        }
    }

    public static int DISPARITY_LEVEL = 255;
    public static int min_disparity = 10;
    public static int max_disparity = 255;

    public static void singleImageTest(String nameImage) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        _path = nameImage;
        String inputSinglePath = "F:/2018-2019/Q1-DSA/demo_projectd/src/main/resources/static/imageDownload/" + nameImage;
        Mat src = Imgcodecs.imread(inputSinglePath, 0);

        imshow("Original Disparity", src);

        String[] tokens = inputSinglePath.split("/");

        Mat adjMap = new Mat();
        src.convertTo(adjMap, CV_8UC1, 2, 1);
        Mat falseColorsMap = new Mat();
        applyColorMap(adjMap, falseColorsMap, COLORMAP_JET);
        imshow("Color Image", falseColorsMap);

        int i = 0;
        Mat output = getObstacleRegion(src.clone(), i);
        long excetute_time = System.currentTimeMillis();
        float excetuteTime = (System.currentTimeMillis() - excetute_time) / 1000F;

        System.out.println("Processing time: " + excetuteTime * 1000 + " ms\n");

        showColorMap(output, "Obstacle Regions");


        output.release();
        src.release();

    }

    public static void sequenceImageTest() {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat src;
        String[] path_tmp = new String[4096];
        String inputSequencePath = "F:/Practical Documents/Internship Project/Disparity/KITTI2012-t-05-25/%06d.png";

        int acc = 1;

        double avg_time = 0.0;
        int start = 1, end = 193;
        for (int i = start; i <= end; i++) {

            System.out.format(inputSequencePath + "\n", i);

            path_tmp[i - 1] = String.format(inputSequencePath, i);

            String[] tokens = path_tmp[i - 1].split("/");
            _path = tokens[tokens.length - 1];

            src = Imgcodecs.imread(path_tmp[i - 1], 0);

            imshow("Original Disparity", src);

            Mat adjMap = new Mat();
            src.convertTo(adjMap, CV_8UC1, 2, 1);
            Mat falseColorsMap = new Mat();
            applyColorMap(adjMap, falseColorsMap, COLORMAP_JET);
            imshow("Color Image", falseColorsMap);

            long excetute_time = System.currentTimeMillis();

            Mat output = getObstacleRegion(src.clone(), i);

            float excetuteTime = (System.currentTimeMillis() - excetute_time) / 1000F;
            avg_time += excetuteTime;

            System.out.println("Processing time: " + excetuteTime * 1000 + " ms\n");

            showColorMap(output, "Obstacle Regions");

            Mat colorOutput = new Mat();
            cvtColor(output, colorOutput, COLOR_GRAY2BGR);

            //imwrite("F:\\Practical Documents\\Internship Project\\Disparity\\Obstacle Regions\\Gray_Image.jpg", colorOutput);
            /*std::vector<int> compression_params;
            compression_params.push_back(CV_IMWRITE_JPEG_QUALITY);
            compression_params.push_back(100);

            int a = i;
            stringstream ss;
            ss << a;
            string str = ss.str();

            cv::imwrite("C:\\Users\\Administrator\\Desktop\\Hoc\\Anh\\" + str + ".jpg", showColorMap(output, "Obstacle Regions"), compression_params);*/

            output.release();
            src.release();

        }

        System.out.println("Average Processing time ************ = " + avg_time / (end - start) * 1000 + "ms\n");
    }

    public static Mat getObstacleRegion(Mat src, int i) {
        //Mat srcClone = src.clone();
        Mat vDisImg_tmp = generateVdisparity(src);
        imshow("V disparity", vDisImg_tmp);
        //waitKey(0);

        Mat filterVDisparityImg = filterUVDisparity(vDisImg_tmp);
        imshow("filterVDisparityImg", filterVDisparityImg);
        //waitKey(0);

        Mat vDisImg = new Mat();
        threshold(vDisImg_tmp, vDisImg, 50, 100, 3);

        Vector<Point> m_vecLinePoint = new Vector<Point>();

        extractGroundPoint(vDisImg, m_vecLinePoint);
        //extractGroundPoint_notAuto(vDisImg, m_vecLinePoint);

        Vec4f m_vec4fLine = new Vec4f();
        fitLineRansac123(m_vecLinePoint, m_vec4fLine);

        rmGround(m_vec4fLine, src);

        rmSky(m_vec4fLine, src);

        Mat uDisparity = computeUdisparity(src, 255);
        imshow("U Disparity", uDisparity);
        //waitKey(0);

        removeObstancleByUDisparity(uDisparity, src, i);

        return src;
    }

    public static void removeObstancleByUDisparity(Mat uDisparity, Mat originalDisparity, int i) {
        Mat filterUDisparityImg = filterUVDisparity(uDisparity);
        imshow("filterUDisparityImg", filterUDisparityImg);
        //waitKey(0);

        Mat uDisparityCanny = new Mat();
        Canny(filterUDisparityImg, uDisparityCanny, 5, 50);
        //Canny(uDisparity, uDisparityCanny, 5, 50);

        for (int col = 0; col < uDisparityCanny.cols(); col++) {
            Vector<Integer> listObstacleDisparity = new Vector<Integer>();
            int index = 0;

            for (int row = 0; row < uDisparityCanny.rows(); row++) {
                int uDisValue = (int) uDisparityCanny.get(row, col)[0];
                if (uDisValue > 0) {
                    listObstacleDisparity.add(row);
                    index += 1;
                }
            }

            for (int row = 0; row < originalDisparity.rows(); row++) {

                int originalDis = (int) originalDisparity.get(row, col)[0];
                if (checkObstacle(listObstacleDisparity, originalDis, 4)) {
                    return;
                } else {
                    originalDisparity.put(row, col, 0);
                }
            }
        }
    }

    public static boolean checkObstacle(Vector<Integer> listObstacleDisparity, int originalDis, int range) {
        for (int i = -range; i <= range; i++) {
            if (isInList(listObstacleDisparity, originalDis + i)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isInList(Vector<Integer> listObstacleDisparity, int value) {
        for (int i = 0; i < listObstacleDisparity.size(); i++) {
            if (listObstacleDisparity.get(i) == value && i != (listObstacleDisparity.size() - 1)) {
                return true;
            }
        }
        return false;
    }


    public static Mat computeUdisparity(Mat disImg, int maxDisparity) {
        Mat uDisparity = new Mat(maxDisparity, disImg.cols(), CV_8UC1, new Scalar(0));

        for (int col = 0; col < disImg.cols(); col++) {
            for (int row = 0; row < disImg.rows(); row++) {
                var disValue = (int) disImg.get(row, col)[0];
                uDisparity.put(disValue, col, uDisparity.get(disValue, col)[0] + 1);

            }
        }
        return uDisparity;
    }

    public static void rmSky(Vec4f vec4fLine, Mat imgDisp8) {

        double slope = vec4fLine.dpX / vec4fLine.dpY;
        double orig = vec4fLine.pX - slope * vec4fLine.pY;

        orig = orig - 3;
        slope = -5;

        for (int u = 0; u < imgDisp8.rows(); u++) {
            for (int v = 0; v < imgDisp8.cols(); v++) {
                var value = imgDisp8.get(u, v)[0];

                if (u < (orig + slope * value)) {
                    imgDisp8.put(u, v, 0);
                }
            }
        }
    }

    public static void rmGround(Vec4f vec4fLine, Mat imgDisp8) {

        Mat m_imgGround = new Mat(imgDisp8.size(), CvType.CV_8U, new Scalar(0));

        int groundMagin = 10;

        double m_dGroundVdispSlope;
        double m_dGroundVdispOrig;


        double slope = vec4fLine.dpX / vec4fLine.dpY;
        double orig = vec4fLine.pX - slope * vec4fLine.pY;

        if (slope > 2) {
            slope = slope - 0.3;
        }
        m_dGroundVdispOrig = orig;
        m_dGroundVdispSlope = slope;
        m_dGroundVdispOrig += 5;

        if (m_dGroundVdispOrig < 0) {
            m_dGroundVdispOrig = 0;
        }

        for (int u = (int) m_dGroundVdispOrig; u < imgDisp8.rows(); u++) {
            for (int v = 0; v < imgDisp8.cols(); v++) {
                if (u > 0 && v > 0) {
                    int value = (int) imgDisp8.get(u, v)[0];
                    double test = m_dGroundVdispOrig + m_dGroundVdispSlope * value - u;

                    if (test > groundMagin) {
                        imgDisp8.put(u, v, value);
                    } else {
                        imgDisp8.put(u, v, 0);
                        m_imgGround.put(u, v, value);
                    }
                }
            }
        }
    }

    public static void fitLineRansac123(Vector<Point> vecLinePoint, Vec4f vec4fLine) {
        int iterations = 100;
        double sigma = 1.;
        double a_max = 7.;


        int n = vecLinePoint.size();

        if (n < 2) {
            System.out.println("Points must be more than 2 EA\n");
        }

        Random rng = new Random();
        rng.setSeed(100);
        double bestScore = -1.;

        for (int k = 0; k < iterations; k++) {
            int i1 = 0, i2 = 0;
            while (i1 == i2) {
                i1 = rng.nextInt(n);
                i2 = rng.nextInt(n);
            }

            Point p1 = vecLinePoint.get(i1);
            Point p2 = vecLinePoint.get(i2);

            Point dp = subtractPoint2f(p2, p1);
            double normValue = 1. / norm(dp);

            dp.x *= normValue;
            dp.y *= normValue;

            /*dp.x *= (1. / norm(dp));
            dp.y *= (1. / norm(dp));*/


            if (dp.x == 0) {
                if (dp.y > 0) dp.x = Math.random();
                else if (dp.y == 0) {
                    dp.x = Math.random();
                    dp.y = Math.random();
                } else dp.x = -Math.random();
            } else if (dp.x > 0) {
                if (dp.y == 0) {
                    dp.y = Math.random();
                }
            } else if (dp.x < 0) {
                if (dp.y == 0) {
                    dp.y = -Math.random();
                }
            }

            double score = 0;

            if (Math.abs(dp.y / dp.x) <= a_max) {
                for (int i = 0; i < n; i++) {
                    Point v = subtractPoint2f(vecLinePoint.get(i), p1);
                    double d = v.y * dp.x - v.x * dp.y;
                    score += Math.exp(-0.5 * d * d / (sigma * sigma));
                }
            }

            if (score > bestScore) {
                vec4fLine.addVec4f(dp.x, dp.y, p1.x, p1.y);
                bestScore = score;
            }
        }
        //System.out.println(bestScore+"\n");
    }

    public static double norm(Point pt) {
        return Math.sqrt(pt.x * pt.x + pt.y * pt.y);
    }

    public static Point subtractPoint2f(Point p2, Point p1) {
        return new Point(p2.x - p1.x, p2.y - p1.y);
    }

    public static void extractGroundPoint(Mat imgVdisp, Vector<Point> vecLinePoint) {
        vecLinePoint.clear();
        int vanishing_point = findVanishingRow(imgVdisp);
        for (int u = vanishing_point; u < imgVdisp.rows(); u++) {
            for (int v = 0; v < imgVdisp.cols(); v++) {
                double value = imgVdisp.get(u, v)[0];
                if (value > 0) {
                    vecLinePoint.add(new Point((float) u, (float) v));
                }
            }
        }
        int t = vecLinePoint.size();
        System.out.println("Vanishing point: " + vanishing_point);
    }

    public static int findVanishingRow(Mat inputImage) {
        int result = 0;

        for (int row = inputImage.rows() - 1; row >= 0; row--) {
            double sum = 0.0;
            for (int col = 5; col < 20; col++) {
                double intensity = inputImage.get(row, col)[0];
                sum += intensity;
            }
            if (sum > 100) {
                return row;
            }
        }
        return result;
    }

    public static Mat filterUVDisparity(Mat uDisparity) {
        Mat kernel = new Mat(3, 3, CV_32FC1, new Scalar(0));
        Mat result = new Mat(uDisparity.rows(), uDisparity.cols(), CV_8UC1);


        kernel.put(0, 0, -1);
        kernel.put(0, 1, -1);
        kernel.put(0, 2, -1);

        kernel.put(1, 0, -1);
        kernel.put(1, 1, 9);
        kernel.put(1, 2, -1);

        kernel.put(2, 0, -1);
        kernel.put(2, 1, -1);
        kernel.put(2, 2, -1);


        filter2D(uDisparity, result, -1, kernel, new Point(-1, -1), 0, BORDER_DEFAULT);

        return result;
    }

    public static Mat generateVdisparity(Mat originalDisImg) {
        Mat vDisImg = new Mat(originalDisImg.rows(), DISPARITY_LEVEL, CV_8UC1);

        for (int y = 0; y < vDisImg.rows(); y++) {
            for (int x = 0; x < vDisImg.cols(); x++) {
                vDisImg.put(y, x, 0);
            }
        }
        for (int y = 1; y < originalDisImg.rows() - 1; y++) {
            for (int x = 1; x < originalDisImg.cols() - 1; x++) {

                int[] localIntensityList = new int[255];

                for (int a = 0; a < 255; a++) {
                    localIntensityList[a] = 0;
                }

                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        localIntensityList[(int) originalDisImg.get(y + i, x + j)[0]] += 1;
                    }
                }

                int maxValue = -1;
                int dipsparityResult = 0;
                for (int a = 0; a < 255; a++) {
                    if (maxValue < localIntensityList[a]) {
                        maxValue = localIntensityList[a];
                        dipsparityResult = a;
                    }
                }
                if (dipsparityResult > min_disparity && dipsparityResult < max_disparity) {
                    vDisImg.put(y, dipsparityResult, vDisImg.get(y, dipsparityResult)[0] + 1);
                }
            }
        }
        return vDisImg;
    }

    public static void showColorMap(Mat src, String windowName) {

        Mat mask = new Mat(new Size(3, 3), CvType.CV_8U);
        MinMaxLocResult tmp = minMaxLoc(src, null);

        Mat adjMap = new Mat();
        double scale = 255 / (tmp.maxVal - tmp.minVal);
        src.convertTo(adjMap, CV_8UC1, 2, 1);
        Mat falseColorsMap = new Mat();
        applyColorMap(adjMap, falseColorsMap, COLORMAP_JET);

        imshow(windowName, falseColorsMap);

        Imgcodecs.imwrite("F:/2018-2019/Q1-DSA/demo_projectd/src/main/resources/static/imageDetect/" + _path, falseColorsMap);
//        waitKey(0);
        falseColorsMap.release();

    }

}



