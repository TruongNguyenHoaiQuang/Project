<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: ThinkPad T430S
  Date: 6/23/2020
  Time: 10:50 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Image Read and Show Example</title>
</head>
<body>
<div class="input-wrap">
    <div class="custom-file-upload">
        <img id="imageSrc" alt="No Image" class="small" src="" style="width: 500px; height: 160px;">
        <div class="caption">imageSrc <input type="file" id="fileInput" name="file" accept="image/*"></div>
        <canvas id="canvasOutput" class="small" height="300px"></canvas>
        <div class="caption">canvasOutput</div>
    </div>
</div>
<script src="js/utils.js" type="text/javascript"></script>
<script type="text/javascript">
    let imgElement = document.getElementById('imageSrc');
    let inputElement = document.getElementById('fileInput');
    inputElement.addEventListener('change', (e) => {
        imgElement.src = URL.createObjectURL(e.target.files[0]);
    }, false);

    imgElement.onload = function() {
        let mat = cv.imread(imgElement);
        cv.imshow('canvasOutput', mat);
        mat.delete();
    };

    function onOpenCvReady() { // eslint-disable-line no-unused-vars
        document.getElementById('status').innerHTML = '<b>OpenCV.js is ready</b>.' +
            'You can upload an image.<br>' +
            'The <b>imageSrc</b> is a &lt;img&gt; element used as cv.Mat input. ' +
            'The <b>canvasOutput</b> is a &lt;canvas&gt; element used as cv.Mat output.';
    }

    function onOpenCvError() { // eslint-disable-line no-unused-vars
        let element = document.getElementById('status');
        element.setAttribute('class', 'err');
        element.innerHTML = 'Failed to load opencv.js';
    }
</script>
<script async="" src="https://docs.opencv.org/master/opencv.js" type="text/javascript" onload="onOpenCvReady();" onerror="onOpenCvError();"></script>
<script src="js/1Js.js"></script>
</body>
</html>
