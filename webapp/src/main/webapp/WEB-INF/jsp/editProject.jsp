<%@ include file="./inc/_taglibs.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<struct:htmlWrapper navi="backlog">
<jsp:body>

<aef:backlogBreadCrumb backlog="${project}" />

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

<div class="structure-main-block project-color-header" id="backlogInfo">

<ul class="backlogTabs">
  <li class="">
  	<a href="#backlogDetails"><span><img alt="Edit" src="static/img/info.png" /> Info</span></a>
  </li>
  <!-- <li class=""><a href="#backlogAssignees"><span><img alt="Edit" src="static/img/team.png" /> Assignees</span></a></li>-->
  <li id="projectActions" class="ui-state-disabled dynamictable-captionaction ui-corner-all" style="float: right; opacity: 1 !important; filter: alpha(opacity = 100) !important; border-width: 1px !important;">
    Actions
  </li>
</ul>

<div class="details" id="backlogDetails" style="overflow: auto;"></div>
<!-- <div class="details" id="backlogAssignees"></div> -->

</div>


<script type="text/javascript">
var agilefantTimesheetsEnabled = ${settings.hourReportingEnabled};

$(document).ready(function() {
  $("#backlogInfo").tabs();
  
  $("#releaseContents").tabs({
    cookie: { name: 'agilefant-project-tabs' }
  });
  
  var controller = new ProjectController({
    id: ${project.id},
    tabs: $("#releaseContents"),
    projectDetailsElement: $("#backlogDetails"),
    textFilterElement: $('#searchByText')
  });

  var actionMenu = $('<ul class="actionCell backlogActions"/>').appendTo(document.body).hide();
  
  $('<li/>').text('Spent effort').click(function() {
    closeMenu();
    controller.openLogEffort();
  }).appendTo(actionMenu);

  $('<li/>').text('Delete').click(function() {
    closeMenu();
    controller.removeProject();
  }).appendTo(actionMenu);

  
  var closeMenu = function() {
    actionMenu.fadeOut('fast');
    actionMenu.menuTimer('destroy');
  };
  
  var openMenu = function(element) {
    var pos = $("#projectActions").offset();
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

 $('#projectActions').click(function() { openMenu(); });

  
});
</script>

<div style="margin-top: 3em;" class="structure-main-block project-color-header" id="releaseContents">
<ul class="backlogTabs">
  <li class=""><a href="#storyTreeContainer"><span><img
        alt="Edit" src="static/img/story_tree.png" /> Story tree</span></a></li>
  <li class=""><a href="#stories"><span><img
				alt="Edit" src="static/img/leaf_stories.png" /> Leaf stories</span></a></li>
  <li class=""><a href="#iterations"><span><img
				alt="Edit" src="static/img/backlog.png" /> Iterations</span></a></li>
  <li class=""><a href="#import"><span><img
				alt="Edit" src="static/img/add_theme.png" /> MM-Project - Import/Export Stories</span></a></li>              
  <li id="searchByText" style="float: right;"> </li>
</ul>

<form onsubmit="return false;">
  <div class="details projectStoryTreeContainer" id="storyTreeContainer" style="position: relative;">
  <c:choose>
    <c:when test="${empty stories}">
      <p class="instructionText">Create stories and organize them as a tree using drag & drop.</p>
    </c:when>
    </c:choose>
  </div>
  <div class="details" id="stories">
  <c:choose>
    <c:when test="${empty leafStories}">
      <div class="static backloglink">
        <p class="instructionText">Create stories by clicking the 'Create story' button
           , or go to the <a href="editBacklog.action?backlogId=${project.parent.id}#leafStories">product</a> and move some here!</p>
      </div>
    </c:when>
    <c:otherwise>
      <p class="instructionText">Prioritize in-project leaf stories as a list using drag & drop.</p>
    </c:otherwise>
  </c:choose>
  </div>
  <div class="details" id="iterations">
  		<div id="iterations">&nbsp;
  		<c:choose>
          <c:when test="${empty iterations}">
            <p class="instructionText">Develop the project via iterations.</p>
          </c:when>
        </c:choose>
  		</div>
  </div>
</form>
  <div class="details" id="import">
      <div style="display:inline;vertical-align: middle">
      <img src="static/img/utt.png" width="50" heigth="50">
      <h1>MM Project</h1>
      </div>
      <c:choose>
        <c:when test="${empty stories}">
          <p class="instructionText">This project hasn't stories. Create at least one story before.</p> 
        </c:when>
        <c:otherwise>
            <p class="instructionText">Export stories from Backlog to Items XML file for MM-Project mobile app.</p> 
              <form action="generateItemExport.action">
                  <input type="hidden" value="${project.id}" name="id" />
                  <input type="submit" value="Export Stories from project ${project.name}" class="dynamics-button" />
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
</div>



<p style="text-align: center;">
	<img src="drawProjectBurnup.action?backlogId=${project.id}" id="bigChart" width="780" height="600" />
</p>

<div class="iterationStoryToolTip">
	<span>Stories in an iteration can't have child stories.</span>
</div>
</jsp:body>
</struct:htmlWrapper>
