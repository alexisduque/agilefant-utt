<%@ include file="./inc/_taglibs.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<struct:htmlWrapper navi="backlog">
<jsp:body>

<aef:backlogBreadCrumb backlog="${iteration}" />

<style>
.details h1 {
  display:inline;
  color:#2E6E9E;
  font-size: 25pt;
}
.details img {
  display: inline;
  margin-right : 15px;
}
</style>

<div class="structure-main-block" id="backlogInfo">
<ul class="backlogTabs">
  <li class=""><a href="#backlogDetails"><span><img
    alt="Edit" src="static/img/info.png" /> Info</span></a></li>
  <li class=""><a href="#backlogAssignees_cont"><span><img
    alt="Edit" src="static/img/team.png" /> Workload</span></a></li>
  <li class=""><a href="#import"><span><img
     alt="Edit" src="static/img/add_theme.png" /> MM-Project - Import/Export Stories</span></a></li> 
  <!-- <li class=""><a href="#iterationHistory"><span>History</span></a></li> --> <!-- Hide history tab until it's fixed to show right info -->
  <li id="iterationActions" class="ui-state-disabled dynamictable-captionaction ui-corner-all" style="float: right; opacity: 1 !important; filter: alpha(opacity = 100) !important; border-width: 1px !important;">
    Actions
  </li>
  
  
</ul>


<div class="details" id="backlogDetails" style="overflow: auto;">
<div id="detailContainer" style="width: 65%; float: left; padding: 8px;"></div>
<div style="width: 28%; float: right">
<div class="smallBurndown" style="background-image: url('drawSmallIterationBurndown.action?backlogId=${iteration.id}');">
 &nbsp;
</div>
<div id="iterationMetrics"><%@ include
  file="./inc/iterationMetrics.jsp"%></div>
</div>
</div>
<div class="details" id="backlogAssignees_cont">
    <div class="details" id="backlogAssignees"></div>
    Iteration availability denotes how unassigned load should be divided within this iteration. If all assignees have the same iteration availability they will receive the same amount of unassigned load.
    <br/>
    Personal adjustment adjusts the iteration baseline load for each user.
  </div>
<div class="details" id="import">
      <div style="display:inline;vertical-align: middle">
      <img src="static/img/utt.png" width="50" heigth="50">
      <h1>MM Project</h1>
      </div>
      <c:choose>
        <c:when test="${empty stories}">
            <p class="instructionText">This Iteration hasn't stories. Create at least one story before.</p> 
        </c:when>
        <c:otherwise>
            <p class="instructionText">Export stories from this iteration to Items XML file for MM-Project mobile app.</p> 
              <form action="generateItemExportIteration.action">
                  <input type="hidden" value="${iteration.id}" name="id" />
                  <input type="submit" value="Export Stories from iteration ${iteration.name}" class="dynamics-button" />
              </form>
            <p class="instructionText">Import XML file from MM-Project mobile app to add stories description .</p>
            <s:form action="storyResultAction" namespace="/" method="POST" enctype="multipart/form-data">
              <s:file name="fileUpload" label="Select an xml to upload" size="40" />
              <s:submit class="dynamics-button" value="Import Stories Description" name="submit" 
              onClick="return confirm('Your current stories will be modified. Are you sure you want to import more data to them?');"/>
            </s:form>
        </c:otherwise>
      </c:choose>
  </div>
  <!-- <div class="details" id="iterationHistory">
    <div style="text-align:center; vertical-align: middle;">
      <img src="static/img/pleasewait.gif" style="display: inline-block; vertical-align: middle;"/> Loading...
    </div>
</div>-->
</div>

<c:choose>
  <c:when test="${empty stories}">
    <c:choose>
      <c:when test="${not empty iteration.parent}">
      <p class="instructionText">Create stories by clicking the 'Create story' button
           , or go to the <a href="editBacklog.action?backlogId=${iteration.parent.id}#stories">project</a>
           or the <a href="editBacklog.action?backlogId=${iteration.parent.parent.id}#leafStories">product</a> and move some here!</p>
      </c:when>
      <c:otherwise>
        <p class="instructionText">Create stories by clicking the 'Create story' button!</p>
      </c:otherwise>
    </c:choose>
  </c:when>
</c:choose>

<script type="text/javascript">
$(document).ready(function() {
 
  $("#backlogInfo").tabs();
  var controller = new IterationController({
      id: ${iteration.id}, 
      storyListElement: $('#stories'), 
      backlogDetailElement: $('#detailContainer'),
      smallBurndownElement: null,
      burndownElement: null,
      assigmentListElement: $("#backlogAssignees"),
      hourEntryListElement: $("#backlogSpentEffort"),
      taskListElement: $("#tasksWithoutStory"),
      metricsElement: $("#iterationMetrics"),
      smallBurndownElement: $("#smallChart"),
      burndownElement: $("#bigChart"),
      tabs: $("#backlogInfo"),
      //historyElement: $("#iterationHistory")
  });

  /* Actions menu */
  var actionMenu = $('<ul class="actionCell backlogActions"/>').appendTo(document.body).hide();
  $('<li/>').text('Spent effort').click(function() {
    closeMenu();
    controller.openLogEffort();
  }).appendTo(actionMenu);

  $('<li/>').text('Export').click(function() {
    closeMenu();
    window.location="exportIteration.action?iterationId=${iterationId}";
  }).appendTo(actionMenu);
 
  $('<li/>').text('Delete').click(function() {
    closeMenu();
    controller.removeIteration();
  }).appendTo(actionMenu);

  $('<li/>').text('Share').click(function() {
	    closeMenu();
	    controller.shareIteration();
  }).appendTo(actionMenu);
  
  $('<li/>').text('Unshare').click(function() {
	    closeMenu();
	    controller.unshareIteration();
  }).appendTo(actionMenu);
  
  var closeMenu = function() {
    actionMenu.fadeOut('fast');
    actionMenu.menuTimer('destroy');
  };
  var openMenu = function(element) {
    var pos = $("#iterationActions").offset();
    actionMenu.css({
      position: 'absolute',
      top: pos.top + 20,
      left: pos.left
    });

    actionMenu.show();
    actionMenu.menuTimer({
      closeCallback: function() {
        closeMenu();
      }
    });
  };

 $('#iterationActions').click(function() { openMenu(); });
 
 var d = new Date();
 $("#chartid").attr("src", $("#chartid").attr('src') + -d.getTimezoneOffset());
 $("#chartlink").attr("href", $("#chartlink").attr('href') + -d.getTimezoneOffset());
});
</script>

<div id="iterationPleasewait" style="height: 200px; width: 100%; position: relative;">
  <div id="iterationLoadingOverlay" class="loadingOverlay">
    <div class="pleaseWait" style="text-align:center;"><img src="static/img/pleasewait.gif" style="display:inline-block;vertical-align:middle;"/><span style="font-size:100%;color:#666;vertical-align: middle;">Loading...</span></div>
    <div class="overlay"></div>
  </div>
</div>

<form onsubmit="return false;"><div id="stories" class="structure-main-block">&nbsp;</div></form>

<form onsubmit="return false;"><div id="tasksWithoutStory" class="structure-main-block">&nbsp;</div></form>

<p style="text-align: center;"><img id="chartid" src="drawIterationBurndown.action?backlogId=${iteration.id}&timeZoneOffset="
	id="bigChart" width="780" height="600" />
	<br>
	<a id="chartlink" href="drawCustomIterationBurndown.action?backlogId=${iteration.id}&customBdWidth=1280&customBdHeight=1024&timeZoneOffset=">Enlarge</a></p>

  </jsp:body>
</struct:htmlWrapper>
