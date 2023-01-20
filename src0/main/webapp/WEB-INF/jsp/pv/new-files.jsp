
   <div class="col-xs-12 col-md-offset-0"  >
      <label  class="control-label">Fichier/file*</label>
      <div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 200px; max-height: 200px;">
           <div  addedFile class="fileinput-new thumbnail" style="max-width: 195px; max-height: 195px;">
                <canvas class="pdf-canvas-view" style="max-width: 180px; max-height: 190px;"></canvas>
           </div>
      </div>
      <div>
           <input name="id_file[]" type="file" class="id_file form-control backing-doc" accept="application/pdf" />
      </div>
      <button class="boutonDelette pull-right" type="button" onclick="delete_file(this.value);" >
                  <i>-</i>
      </button>
   </div>
   </br></br>