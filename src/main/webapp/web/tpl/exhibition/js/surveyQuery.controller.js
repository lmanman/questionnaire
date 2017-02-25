
angular.module('app.exhibition.surveyQuery.controllers', [])
    .controller('SurveyQueryCtrl', ['$scope','$location','$log','$stateParams','$http',ctrlFn]);
;


function ctrlFn($scope,$location,$log,$http){

    $scope.dynamicType = function(a,b){
        var type;
        var value = a/b*100;
        if (value < 25) {
            type = 'danger';
        } else if (value < 50) {
            type = 'warning';
        } else if (value < 75) {
            type = 'info';
        } else {
            type = 'success';
        }
        return type;
    }

    $scope.exhibitionSurveyList = [
        {"id":"1","exhibitionName":"顾家沙发汶水店","address":"上海市宝山区汶水路1555号","dealerName":"经销商A","categoryName":"家具-客厅家具-皮质-新中式-非进口","brandName":"顾家","cityName":"上海","updateDate":"2016-11-22","schedule":[24,64],"userName":"Gavyn"},
        {"id":"2","exhibitionName":"慕斯真北店","address":"上海市普陀区真北路1108号","dealerName":"经销商B","categoryName":"家居饰品-寝具-乳胶床垫-其他-非进口","brandName":"慕斯","cityName":"上海","updateDate":"2016-12-16","schedule":[64,64],"userName":"WangNingy"},
    ];
    $scope.dealerList = [
        {"id":"1","name":"经销商A","linkman":"张三","telephone":"18711111111","email":"zs@visionet.com.cn"},
        {"id":"2","name":"经销商B","linkman":"李四","telephone":"18722222222","email":"ls@visionet.com.cn"},
    ];
    $scope.brandList = [
        {"id":"1","name":"圣象","mainId":2,"mainName":"建材"},
        {"id":"2","name":"慕斯","mainId":3,"mainName":"家居饰品"},
        {"id":"3","name":"顾家","mainId":1,"mainName":"家具"},
        {"id":"4","name":"科勒","mainId":2,"mainName":"建材"},
        {"id":"5","name":"海尔","mainId":4,"mainName":"电器"},
    ];
    $scope.cityList = [
        {"id":1,"cityName":"北京"},
        {"id":2,"cityName":"上海"},
        {"id":3,"cityName":"广州"},
        {"id":4,"cityName":"深圳"},
    ];


    $scope.newSurvey = function() {
        $location.path('/app/exhibition/surveyInput/0');
    };

    function submit(){

    }

}
