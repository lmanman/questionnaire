
angular.module('app.console.controllers', [])
    .controller('UserCtrl', ['$scope','$stateParams','$http','$state','$log',ctrlFn]);
;


function ctrlFn($scope,$stateParams,$http,$state,$log){
    $scope.uid = 0;

    $scope.formData = {
        pageInfo: {
            pageNumber: 1,
            pageSize: 10
        }
    };
    $scope.list = [];

    $scope.password="";
    $scope.password2="";
    $scope.confirm_password2="";


    $scope.userInfo = {};
    $scope.detail = function(){
        $scope.uid = $stateParams.uid;
        if($scope.uid!=0 && $scope.uid!=''){
            $scope.password2="1";
            $scope.confirm_password2="1";
            $http.get($scope.app.projectName + '/mobile/user/'+$scope.uid).success(function(result){
                //$log.info(result);

                $scope.userInfo = result;

                angular.forEach($scope.userInfo.roleList, function(role){
                    if(role.hasRole){
                        $scope.chkVal[role.id]=true;
                    }else{
                        $scope.chkVal[role.id]=false;
                    }
                });
            }).error(function(){
                alert('请求失败');
            });


        }else if($scope.uid==0){
            $http.get($scope.app.projectName + '/console/role/list').success(function(result){
                //$log.info(result);

                $scope.userInfo.roleList = result;
            }).error(function(){
                alert('权限数据更新失败！');
            });
        }
    };

    $scope.query  = function(){
        var formData = {};
        formData.pageInfo = $scope.formData.pageInfo;
        formData.queryName = $scope.queryName;

        $http.post($scope.app.projectName + '/mobile/user/search',formData).success(function(result){
            $scope.list = result;
        }).error(function(){
            alert('查询失败');
        });
        //console.log("=="+String.fromCharCode(11)+"--");
    };

    $scope.chkVal = {};
    $scope.saveInfo = function(){
        var formData = {};
        formData.loginName = $scope.userInfo.loginName;
        formData.aliasName = $scope.userInfo.aliasName;
        formData.phoneNumber = $scope.userInfo.phoneNumber;
        formData.email = $scope.userInfo.email;
        formData.remark = $scope.userInfo.remark;

        var url = '';
        if($scope.uid>0) {
            formData.id = $scope.userInfo.id;
            url = $scope.app.projectName + '/mobile/user/update';
        }else if($scope.uid==0){
            url = $scope.app.projectName + '/mobile/user/register';
            formData.plainPassword = $scope.password2;
        }
        formData.roleSet = [];
        angular.forEach($scope.userInfo.roleList, function(role){
            if($scope.chkVal[role.id]){
                formData.roleSet.push({"id":role.id})
            }
        });

        $log.info(formData.roleSet);

        $http.post(url, formData).success(function (result) {
            if(result!=null && result.code == undefined){
                result = angular.fromJson(result);
                if(result.code == undefined){
                    result = angular.fromJson(result);
                }
            }
            if (result.code == '10000') {
                alert("保存成功");
                if($scope.uid>0) {
                    $scope.app.aliasName = $scope.userInfo.aliasName;
                }
                $state.go('app.console.userList');
            } else {
                alert(result.msg);
                $state.go('app.console.userList');
            }
        }).error(function () {
            alert('提交失败');
        });
    };

    $scope.savePwd = function(){
        var formData = {"id":$scope.uid,"plainPassword":$scope.password};

        $http.post($scope.app.projectName + '/mobile/user/update/passwd',formData).success(function(result){
            if(result.code=='10000'){
                alert("密码修改成功");
                $state.go('app.console.userList');
            }else{
                alert(result.msg);
            }

        }).error(function(){
            alert('提交失败');
        });
    };

    $scope.delete = function(index,uid){
        if(confirm("是否确认删除！")) {
            $http.get($scope.app.projectName+'/mobile/user/disable/'+uid+'/1').success(function (result) {
                if(result.code=='10000'){
                    alert("删除成功");
                    $scope.query();
                }else{
                    alert(result.msg);
                }
            }).error(function () {
                alert('删除失败');
            });

        }
    };



}
