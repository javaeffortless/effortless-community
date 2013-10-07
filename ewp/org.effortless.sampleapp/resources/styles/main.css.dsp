<%@ taglib uri="http://www.zkoss.org/dsp/web/core" prefix="c" %>
<c:if test="${c:browser('gecko2') || c:browser('ie9') || c:browser('opera') || c:browser('safari')}">

</c:if>

body {
  padding: 0px;
}

.tag {
  vertical-align: middle;
}

.layout-grid {
  vertical-align: middle;
}

.layout-grid .z-rowchildren {
  padding: 2px;
}

.z-div {
  background-color: inherit;
}

.inline-actions .z-button-cm {
  padding: 0 0px;
}

.inline-actions .z-button-tm, .inline-actions .z-button-bm, 
.inline-actions .z-button-cl, .inline-actions .z-button-cr,
.inline-actions .z-button-tl, .inline-actions .z-button-tr,
.inline-actions .z-button-bl, .inline-actions .z-button-br {
  display: none;
}

.filter-container {
  background-color: #A0A0ED;
  border-bottom: 1px solid #a0a0a0;
  padding-bottom: 7px;
}

.finder-window {
}

.finder-window .z-window-embedded-header {
}

.finder-window .z-window-embedded-cnt {
  padding: 0px;
}

.finder-window .finder-row-search {
  padding: 4px;
}

.finder-window .finder-list {
  padding: 4px;
}

.finder-window .finder-buttons {
  padding: 4px;
}

.editor-window {
}

.embedded-filter-window" {
}

.info-window {
}



.vlayout img {
  display: block;
  margin: 0 auto;
  margin-bottom: 4px;
}
.vlayout .z-toolbarbutton-cnt {
  text-align: center;
  color: #1C5178;
  font-size: 10px;
  font-family: arial, sans-serif;
  font-weight: normal;
}

.msg-delete {
  background-image : url(../img/deleteConfirm.png);
  width: 48px; height: 48px;
}

.msg-info { 
  background-image : url(../img/infoConfirm.png);
  width: 48px; height: 48px;
}

.msg-warning { 
  background-image : url(../img/warningConfirm.png);
  width: 48px; height: 48px;
}

.icon-info {
	background-image : url(../img/info.png);
	width: 16px; height: 16px;
}

.main-windows .z-tabpanel {
padding: 0px;
}

.z-hlayout-right {
  text-align: right;
}

div.z-listbox-body .z-listcell {
  padding: 0px;
}









.photo-field .z-button-tl, .photo-field .z-button-tr, .photo-field .z-button-bl, .photo-field .z-button-br, .photo-field .z-button-tm, .photo-field .z-button-bm, .photo-field .z-button-cl, .photo-field .z-button-cr, .photo-field .z-button-cm {
  background-image: none;
}

.photo-field .z-button-cm {
  color: #0000AA;
  text-decoration: underline;
}

.photo-field-readonly .z-button-cm {
  color: #000000;
  text-decoration: none;
}


.photo-field img {
  max-width: 146px;
}





<%--

.wPhotoField {
	padding: 0;
	border: none;
	background-color: inherit;
}

.wPhotoField tr {
	background-color: inherit;
}

.wPhotoField td {
	vertical-align: middle;
	text-align: center;
}

.photoField_properties {
	border:none;
	background-color: inherit;
}

.photoField_properties tr {
	background-color: inherit;
}

.photoField_properties td {
	vertical-align: top;
	text-align: left;
}
.photoField img {
	max-width: 146px;
}

.photoField .z-button-cm {
	cursor: inherit;
}

.photoField .z-button-tl, 
.photoField .z-button-tr, 
.photoField .z-button-bl, 
.photoField .z-button-br,
.photoField .z-button-tm, 
.photoField .z-button-bm,
.photoField .z-button-cl, 
.photoField .z-button-cr {
	background-image:none;
}

.photoField .z-button-cm {
	padding: 0px 0px;
}

.photoField .z-button-cl {
	display: none;
}

.photoField .z-button-cr {
	display: none;
}

.photoField .z-button-bl {
	display: none;
}

.photoField .z-button-bm, 
.photoField .z-button-br,
.photoField .z-button-tm, 
.photoField .z-button-tr {
	display: none;
}

.photoField_null .z-button-cm {
	background: url(${c:encodeURL('~./blacklabel/img/photo/noImage.gif')}) no-repeat scroll 0 0 transparent;
	width: 146px;
	height: 196px;
}

.photoField_null {
	background-color: #FFF9ED;
	padding: 10px;
	border-radius: 6px 6px 6px 6px;
	border: 2px solid #E6E6E6;
	width: 146px;
}

.photoField_notnull {
	background-color: #FFF9ED;
	padding: 10px;
	border-radius: 6px 6px 6px 6px;
	border: 2px solid #E6E6E6;
	width: 146px;
}

.photoField_notnull .z-button-cm {
	border-radius: 6px 6px 6px 6px;
	background-image: none;
}

.photoField_menuitem_btnDownload .z-menuitem-img {
	background: url(${c:encodeURL('~./blacklabel/img/file/download.png')}) no-repeat scroll 0 0 transparent;
}

.photoField_menuitem_btnUpload .z-menuitem-img {
	background: url(${c:encodeURL('~./blacklabel/img/file/upload.png')}) no-repeat scroll 0 0 transparent;
}

.photoField_menuitem_btnProperties .z-menuitem-img {
	background: url(${c:encodeURL('~./blacklabel/img/table/sel.gif')}) no-repeat scroll 0 0 transparent;
}

.photoField_menuitem_btnDelete .z-menuitem-img {
	background: url(${c:encodeURL('~./blacklabel/img/table/del.gif')}) no-repeat scroll 0 0 transparent;
}

.photoField_notpointer {
	cursor: default;
}

.photoField_pointer {
<c:if test="${zk.ie > 0}">
	cursor: hand;
</c:if>
<c:if test="${!(zk.ie > 0)}">
	cursor: pointer;
</c:if>
}

--%>



















.file-field .z-button-tl, .file-field .z-button-tr, .file-field .z-button-bl, .file-field .z-button-br, .file-field .z-button-tm, .file-field .z-button-bm, .file-field .z-button-cl, .file-field .z-button-cr, .file-field .z-button-cm {
  background-image: none;
}

.file-field .z-button-cm {
  color: #0000AA;
  text-decoration: underline;
}

.file-field-readonly .z-button-cm {
  color: #000000;
  text-decoration: none;
}
/*
.z-button-over .z-button-cm {
  background-position: 0 0;
}
*/
.mime-type-pdf .z-button-cl {
  background-image: url(../img/mime/pdf.png);
  width: 16px; height: 16px;
}

.mime-type-audio .z-button-cl {
  background-image: url(../img/mime/audio.png);
  width: 16px; height: 16px;
}

.mime-type-compress .z-button-cl {
  background-image: url(../img/mime/compress.png);
  width: 16px; height: 16px;
}

.mime-type-image .z-button-cl {
  background-image: url(../img/mime/image.png);
  width: 16px; height: 16px;
}

.mime-type-office .z-button-cl {
  background-image: url(../img/mime/office.png);
  width: 16px; height: 16px;
}

.mime-type-unknown .z-button-cl {
  background-image: url(../img/mime/unknown.png);
  width: 16px; height: 16px;
}

.mime-type-video .z-button-cl {
  background-image: url(../img/mime/video.png);
  width: 16px; height: 16px;
}

.mime-type-word .z-button-cl {
  background-image: url(../img/mime/word.png);
  width: 16px; height: 16px;
}



.field-column-widget {
font-weight: bold;
}



.z-textbox-readonly  {
  border: 0px;
  background-color: inherit;
}

.z-textbox-disd {
  color: #000000 !important;
}

.z-datebox-readonly {
  border: 0px;
  background-color: inherit;
}

.z-datebox-disd, .z-datebox-disd * {
  color: #000000 !important;
}

.z-datebox-real-readonly {
  background-color: inherit;
}

.z-combobox-readonly {
  border: 0px;
  background-color: inherit;
}

.z-combobox-disd, .z-combobox-disd * {
  color: #000000 !important;
}

.z-combobox-real-readonly {
  background-color: inherit;
}

.horizontal-align .z-hlayout-inner {
  vertical-align: middle;
}


.z-a:active {
  color: #0000EE;
}
