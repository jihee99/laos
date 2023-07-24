
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
        const selectedValue = $('#select-bassin').val();
        console.log(selectedValue);

        $.ajax({
            type: 'POST',
            url: `/run/${selectedValue}`,
            // dataType: 'json',
            // contentType: 'application/json',
            success: (response) => {
                console.log("a");
                console.log(response);
                if (response.RESULT !== 'SUCCESS') {
                    alert(FAILURE_TXT);
                }else{
                    alert(confirmTxt.successTxt);
                    location.replace('/');
                }
            },
            error: (error) => {
                console.error(error)
            }
        });

    });



})(jQuery);