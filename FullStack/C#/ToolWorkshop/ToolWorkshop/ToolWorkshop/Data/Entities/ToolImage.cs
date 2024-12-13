using System.ComponentModel.DataAnnotations;

namespace ToolWorkshop.Data.Entities
{
    public class ToolImage
    {
        public int Id { get; set; }

        public Tool Tool{ get; set; }

        [Display(Name = "Foto")]
        public byte[] ImageData { get; set; }
    }
}
