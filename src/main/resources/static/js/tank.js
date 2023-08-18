document.getElementById("file").addEventListener('change', function() {
    let fileName = this.value.split("\\").pop();
    document.querySelector(".upload-name").value = fileName;
    console.log(fileName);
});

document.getElementById("tank-button").addEventListener('click', function(){
    let selectedFile = document.getElementById("file").files[0]; // 업로드된 파일 가져오기
    let origin = new FormData(document.getElementById("excelform"));

    if(origin.get('file')){
        let fd = new FormData();

        // FormData에 파일 및 데이터 추가
        fd.append('file',origin.get('file'));
        fd.append('floatingSelect', origin.get('floatingSelect'));

        // let form = document.getElementById('excelform');
        // let fd = new FormData(form);

        let xhr = new XMLHttpRequest();
        let url = '/runTank';
        let frmData = new FormData();

        xhr.open('POST', url, true);
        xhr.onload = function () {
            if (xhr.status === 200) {
                console.log(xhr.responseText); // 성공한 경우 서버 응답 출력
            } else {
                console.log('파일업로드 실패');
            }
        };
        xhr.onerror = function (e) {
            console.log(e);
            console.log('파일업로드 실패');
        };

        xhr.send(fd); // FormData를 전송

        // xhr.onreadystatechange = function () {
        //     if (xhr.readyState === XMLHttpRequest.DONE) {
        //         if (xhr.status === 200) {
        //             console.log('File uploaded successfully:', xhr.responseText);
        //         } else {
        //             console.error('Error:', xhr.statusText);
        //         }
        //     }
        // };
        // xhr.send(fd);
    }else{
        alert("파일없다!");
    }


});
