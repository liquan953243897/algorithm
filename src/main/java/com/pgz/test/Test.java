package com.pgz.test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.net.URL;

/**
 * 好像是把浏览器页面生成图片
 *
 * @author liquan_pgz@qq.com
 * @date 2019-12-18
 */
public class Test extends JFrame {

    public Test(String url, File file) throws Exception {
        JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(false);
        editorPane.setPage(url);
        JScrollPane jsp = new JScrollPane(editorPane);
        getContentPane().add(jsp);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(screenSize.width / 2, screenSize.height / 2);
        this.setVisible(true); // 如果这里不设置可见，则里面的图片等无法截取

        // 如果不延时，则图片等可能没有时间下载显示
        // 具体的秒数需要根据网速等调整
        Thread.sleep(5 * 1000);

        setSize(10000, 10000);

        pack();
        // BufferedImage image = new BufferedImage(editorPane.getWidth(),
        // editorPane.getHeight(), BufferedImage.TYPE_INT_RGB);
        BufferedImage image = new BufferedImage(editorPane.getWidth(), editorPane.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = image.createGraphics();
        editorPane.paint(graphics2D);

        BufferedImage image1 = resize(image, 600, 400);

        ImageIO.write(image1, "jpg", file);
        dispose();
    }

    public static void main(String[] args) throws Exception {
//        new Test("https://blog.csdn.net/jgfyyfd/article/details/83821450", new File("d:/file.jpg"));
        generateOutput("https://www.baidu.com/", new File("d:/file1.jpg"));
    }

    public static BufferedImage resize(BufferedImage source, int targetW, int targetH) {
        // targetW，targetH分别表示目标长和宽
        int type = source.getType();
        BufferedImage target = null;
        double sx = (double) targetW / source.getWidth();
        double sy = (double) targetH / source.getHeight();
        // 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
        // 则将下面的if else语句注释即可
        if (sx > sy) {
            sx = sy;
            targetW = (int) (sx * source.getWidth());
            // } else {
            // sy = sx;
            // targetH = (int) (sy * source.getHeight());
        }
        if (type == BufferedImage.TYPE_CUSTOM) { // handmade
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else
            target = new BufferedImage(targetW, targetH, type);
        Graphics2D g = target.createGraphics();
        // smoother than exlax:
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;
    }

    public static void generateOutput(String url, File file) throws Exception {
        JEditorPane ed = new JEditorPane(new URL(url));
        ed.setSize(1000, 1300);
        Thread.sleep(5000);
        //create a new image
        BufferedImage image = new BufferedImage(ed.getWidth(), ed.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        SwingUtilities.paintComponent(image.createGraphics(),
                ed,
                new JPanel(),
                0, 0, image.getWidth(), image.getHeight());
        //save the image to file
        ImageIO.write((RenderedImage) image, "jpg", file);
    }

}
