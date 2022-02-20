window.onload = function(){
    var mapAddress = document.getElementsByClassName('mapAddress');
    var mapSize = document.getElementById('mapSize').value;
    console.log(mapSize);
    var mapEco = document.getElementsByClassName('mapEco');
    var mapVFood = document.getElementsByClassName('mapVFood');
    var mapGFood = document.getElementsByClassName('mapGFood');

    var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new window.kakao.maps.LatLng(37.5662, 126.97865), // 지도의 중심좌표
        level: 3, // 지도의 확대 레벨
        mapTypeId : kakao.maps.MapTypeId.ROADMAP // 지도종류
        };

    // 지도를 생성한다
    var map = new kakao.maps.Map(mapContainer, mapOption);
    // 지도에 확대 축소 컨트롤을 생성한다
    var zoomControl = new kakao.maps.ZoomControl();
    // 지도의 우측에 확대 축소 컨트롤을 추가한다
    map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);



    if (navigator.geolocation) {

        // GeoLocation을 이용해서 접속 위치를 얻어옵니다
        navigator.geolocation.getCurrentPosition(function(position) {

            var lat = position.coords.latitude, // 위도
            lon = position.coords.longitude; // 경도

            var locPosition = new kakao.maps.LatLng(lat, lon), // 마커가 표시될 위치를 geolocation으로 얻어온 좌표로 생성합니다
                              message = ''; // 인포윈도우에 표시될 내용입니다
                              displayMarker(locPosition, message); // 마커와 인포윈도우를 표시합니다
            });

    } else { // HTML5의 GeoLocation을 사용할 수 없을때 마커 표시 위치와 인포윈도우 내용을 설정합니다
        var locPosition = new kakao.maps.LatLng(33.450701, 126.570667),message = '위치설정을 해주세요'
        displayMarker(locPosition, message);
    }

     //지도에 마커와 인포윈도우를 표시하는 함수입니다
    function displayMarker(locPosition, message) {

        // 마커를 생성합니다


        var iwContent = message, // 인포윈도우에 표시할 내용
                        iwRemoveable = true;

        // 인포윈도우를 생성합니다
        var infowindow = new kakao.maps.InfoWindow({
                         content : iwContent,
                         removable : iwRemoveable
       });

        // 인포윈도우를 마커위에 표시합니다
        infowindow.open(map, marker);

        // 지도 중심좌표를 접속위치로 변경합니다
        map.setCenter(locPosition);

                var imageSrc = "/zerogreen/bootstrap/images/map/here.png", // 마커이미지의 주소입니다
                    imageSize = new kakao.maps.Size(10, 10), // 마커이미지의 크기입니다
                    imageOption = {offset: new kakao.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

                // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
                var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption),
                    markerPosition = locPosition; // 마커가 표시될 위치입니다

                // 마커를 생성합니다
                var marker = new kakao.maps.Marker({
                    position: markerPosition,
                    image: markerImage // 마커이미지 설정
                });

                // 마커가 지도 위에 표시되도록 설정합니다
                marker.setMap(map);

        // 지도 중심좌표를 접속위치로 변경합니다
        map.setCenter(locPosition);
        }
        kakao.maps.event.addListener(map, 'zoom_changed', function() {

            // 지도의 현재 레벨을 얻어옵니다
            var level = map.getLevel();
            var message = '현재 지도 레벨은 ' + level + ' 입니다';
            console.log(message);
        });

        for(var j=0;j<=mapSize;j++){
            var address = mapAddress[j].value;

            var geocoder = new kakao.maps.services.Geocoder();
            // 마커이미지의 주소입니다
            var imageSrc = "/zerogreen/bootstrap/images/map/greenpin.png",
                imageSize = new kakao.maps.Size(25, 25), // 마커이미지의 크기입니다
                imageOption = {offset: new kakao.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.
//                if (address == mapEco){
//                    imageSrc = "/zerogreen/bootstrap/images/map/yellowpin.png";
//                }else if (address == mapVFood){
//                    imageSrc = "/zerogreen/bootstrap/images/map/deeppin.png";
//                }else if (address == mapGFood){
//                    imageSrc = "/zerogreen/bootstrap/images/map/greenpin.png";
//                }

            // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
            var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption); // 마커가 표시될 위치입니다
            // 주소로 좌표를 검색합니다
            geocoder.addressSearch(address, function(result, status) {
            // 정상적으로 검색이 완료됐으면
                 if (status === kakao.maps.services.Status.OK) {
                    var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

                    // 결과값으로 받은 위치를 마커로 표시합니다
                    var marker = new kakao.maps.Marker({
                        image: markerImage,
                        position: coords
                    });
                marker.setMap(map);
                }
            });
//                console.log(j+'zzz');
//                console.log(address);
    //            console.log("mapGFood"+mapGFood[j].value);
    //            console.log("mapVFood"+mapVFood[j].value);
    //            console.log("mapEco"+mapEco[j].value);
    }
}