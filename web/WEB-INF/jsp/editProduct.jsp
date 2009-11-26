<%@ include file="./inc/_taglibs.jsp"%>

<struct:htmlWrapper navi="backlog">

<h2>Product: <c:out value="${product.name}"/></h2>

<div class="structure-main-block" id="backlogInfo">
<ul class="backlogTabs">
  <li class=""><a href="#backlogDetails"><span><img
    alt="Edit" src="static/img/info.png" /> Info</span></a></li>
  <c:if test="${settings.hourReportingEnabled}">
  <li class=""><a href="#backlogSpentEffort"><span><img
    alt="Edit" src="static/img/timesheets.png" /> Spent effort</span></a></li>
  </c:if>
  <li class=""><a href="#backlogHistory"><span><img
    alt="Edit" src="static/img/timesheets.png" /> History</span></a></li>
</ul>

<div class="details" id="backlogDetails" style="overflow: auto;"></div>
<div class="details" id="backlogSpentEffort"></div>

</div>


<script type="text/javascript">
$(document).ready(function() {
  $("#backlogInfo").tabs();
  var controller = new ProductController({
    id: ${product.id},
    productDetailsElement: $("#backlogDetails"),
    projectListElement: $("#projects"),
    storyListElement: $('#stories'),
    hourEntryListElement: $("#backlogSpentEffort")
  });
  if(Configuration.isTimesheetsEnabled()) {
  	$("#backlogInfo").bind('tabsselect', function(event, ui) {
	    if (ui.index == 1) {
      	controller.selectSpentEffortTab();
    	}
  	});
  }

  var hideDoneStories = function() {
    var opt = $(this);
    if(opt.is(":checked")) {
      $("#storyTree [storystate=DONE]").hide();
    } else {
      $("#storyTree li").show();
    }
  }
  $("#treeHideDone").change(hideDoneStories);
  
  
  var storyTreeController = new StoryTreeController(
    ${product.id}, "product", $('#storyTree'),
    {
      refreshCallback: function() { hideDoneStories.call($("#treeHideDone"),[]); }
    }
  );
  storyTreeController.refresh();

  window.setInterval(function() {
    storyTreeController.refresh();
  }, 120000);
});
</script>

<div class="ui-widget-content ui-corner-all structure-main-block dynamictable">
  <div class="ui-widget-header ui-corner-all dynamictable-caption-block dynamictable-caption">Story tree</div>
  <div><input id="treeHideDone" type="checkbox"/>Hide done stories</div>
  <form onsubmit="return false;"><div id="storyTree">&nbsp;</div></form>
</div>

<form onsubmit="return false;"><div id="projects" class="structure-main-block">&nbsp;</div></form>



</struct:htmlWrapper>
