/**
 * Created by Abhinav
 */

angular.module('userApp',['ui.router','ngResource','userApp.controllers','userApp.services','ui.bootstrap','userApp.directives','userApp.filters']);

angular.module('userApp').config(function($stateProvider){
    $stateProvider.state('users',{
        url:'/users',
        templateUrl:'user-app/partials/user/users.html',
        controller:'UserListController'
    }).state('viewUser',{
       url:'/users/:id/view',
       templateUrl:'user-app/partials/user/user-view.html',
       controller:'UserViewController'
    }).state('newUser',{
        url:'/users/new',
        templateUrl:'user-app/partials/user/user-add.html',
        controller:'UserCreateController'
    }).state('editUser',{
        url:'/users/:id/edit',
        templateUrl:'user-app/partials/user/user-edit.html',
        controller:'UserEditController'
    }).state('viewLicense',{
       url:'/licenses/:id/view/:userid',
       templateUrl:'user-app/partials/license/license-view.html',
       controller:'LicenseViewController'
    }).state('newLicense',{
       url:'/licenses/new/:userid',
       templateUrl:'user-app/partials/license/license-add.html',
       controller:'LicenseCreateController'
    }).state('editLicense',{
       url:'/licenses/:id/edit/:userid',
       templateUrl:'user-app/partials/license/license-edit.html',
       controller:'LicenseEditController'
    }).state('facilities',{
        url:'/facilities',
        templateUrl:'user-app/partials/facility/facilities.html',
        controller:'FacilityListController'
    }).state('viewFacility',{
        url:'/facilities/:id/view',
        templateUrl:'user-app/partials/facility/facility-view.html',
        controller:'FacilityViewController'
    }).state('newFacility',{
        url:'/facilities/new',
        templateUrl:'user-app/partials/facility/facility-add.html',
        controller:'FacilityCreateController'
    }).state('editFacility',{
        url:'/facilities/:id/edit',
        templateUrl:'user-app/partials/facility/facility-edit.html',
        controller:'FacilityEditController'
    }).state('supervisors',{
        url:'/supervisors',
        templateUrl:'user-app/partials/supervisor/supervisors.html',
        controller:'SupervisorListController'
    }).state('viewSupervisor',{
        url:'/supervisors/:id/view',
        templateUrl:'user-app/partials/supervisor/supervisor-view.html',
        controller:'SupervisorViewController'
    }).state('newSupervisor',{
        url:'/supervisors/new',
        templateUrl:'user-app/partials/supervisor/supervisor-add.html',
        controller:'SupervisorCreateController'
    }).state('editSupervisor',{
        url:'/supervisors/:id/edit',
        templateUrl:'user-app/partials/supervisor/supervisor-edit.html',
        controller:'SupervisorEditController'
    }).state('editStaff',{
        url:'/staffmembers/:id/edit/:facid',
        templateUrl:'user-app/partials/staff-member/staff-member-edit.html',
        controller:'StaffEditController'
    }).state('viewStaff',{
        url:'/staffmembers/:id/view/:facid',
        templateUrl:'user-app/partials/staff-member/staff-member-view.html',
        controller:'StaffViewController'
    }).state('newStaff',{
        url:'/staffmembers/new/:facid',
        templateUrl:'user-app/partials/staff-member/staff-member-add.html',
        controller:'StaffCreateController'
    }).state('editSchedule',{
        url:'/schedules/:id/edit/:facid',
        templateUrl:'user-app/partials/schedule/schedule-edit.html',
        controller:'ScheduleEditController'
    }).state('viewSchedule',{
        url:'/schedules/:id/view/:facid',
        templateUrl:'user-app/partials/schedule/schedule-view.html',
        controller:'ScheduleViewController'
    }).state('newSchedule',{
        url:'/schedules/new/:facid',
        templateUrl:'user-app/partials/schedule/schedule-add.html',
        controller:'ScheduleCreateController'
    }).state('authenticatejwt',{
        url:'/auth',
        templateUrl:'user-app/partials/authenticate/authenticate.html',
        controller:'Main'
    })
}).constant('API', 'http://localhost:8088/oauth/token')
.config(function($httpProvider) {
  $httpProvider.interceptors.push('authInterceptor');
  
}).run(function($state){
   $state.go('authenticatejwt');
});
