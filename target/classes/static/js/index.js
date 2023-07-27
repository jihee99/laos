
// document.addEventListener('DOMContentLoaded', function() {
//     // 버튼 클릭 시 이벤트 리스너 등록
//     document.getElementById('run-btn').addEventListener('click', function() {
//         // 선택한 값 가져오기
//         const selectedValue = document.getElementById('select-bassin').value;
//         // 선택한 값 출력 (예: dc0790 또는 cscal)
//
//         // 여기서 가져온 값을 활용하여 원하는 작업을 수행하면 됩니다.
//         // 예: 선택한 값에 따라 다른 동작을 수행하는 등의 로직을 추가할 수 있습니다.
//     });
// });
//         // AJAX 요청 보내기
//         let xhr = new XMLHttpRequest();
//         let url = 'url-to-your-backend-api'; // 데이터베이스 값을 제공하는 백엔드 API의 URL
//         xhr.open('GET', url + '?bassin=' + selectedValue, true); // GET 요청으로 선택한 값 전달
//
//         xhr.onreadystatechange = function() {
//             if (xhr.readyState === 4) { // 요청이 완료되면
//                 if (xhr.status === 200) { // 성공적인 응답을 받았을 때
//                     let response = JSON.parse(xhr.responseText); // JSON 형식의 응답 파싱
//
//                     // 응답 처리 (예: 결과를 화면에 출력)
//                     let resultDiv = document.getElementById('result');
//                     resultDiv.innerHTML = 'Received data from the server: ' + response;
//                 } else {
//                     console.error('Request failed with status:', xhr.status);
//                 }
//             }
//         };
//
//     });
// });

(function ($) {
    $('#run-btn').click(function(){
        const code = $('#select-basin').val();
        $.ajax({
            url: `/download/${code}`,
            method: "GET",
            // xhrFields: {
            //     responseType: 'blob' // 파일을 binary로 다운로드 받기 위해 responseType을 'blob'으로 설정합니다.
            // },
            success: function(response) {
                //파일 다운로드를 위한 코드
                const blob = new Blob([response]);
                const link = document.createElement('a');
                link.href = URL.createObjectURL(blob);
                link.download = code;
                document.body.appendChild(link);
                link.click();
                document.body.removeChild(link);

            },
            error: function (xhr, status, error) {
                console.error("Error: " + status);
                alert('파일 다운로드에 실패했습니다.');
            }
        });


    });



})(jQuery);