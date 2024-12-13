using System.ComponentModel.DataAnnotations;
using ToolWorkshop.Enums;

namespace ToolWorkshop.Data.Entities
{
    public class Catalog
    {
        [Key]
        [Display(Name = "SKU")]
        public int SKU { get; set; }

        public int ToolId { get; set; }

        public int PlanogramId { get; set; }
        
        [Display(Name = "Herramienta")]
        [Required(ErrorMessage = "El Campo {0} es obligatorio.")]
        public Tool Tool { get; set; }

        [Display(Name = "Planograma")]
        [Required(ErrorMessage = "El Campo {0} es obligatorio.")]
        public Planogram Planogram { get; set; }

        [Display(Name = "Estado")]
        [Required(ErrorMessage = "El Campo {0} es obligatorio.")]
        public CatalogStatus Status { get; set; }

        [Display(Name = "Foto")]
        public byte[] ImageData { get; set; }

        public virtual ICollection<Movement_Detail>? MovementDetails { get; set; }


        [Display(Name = "Herramienta")]
        public string FullName => SKU != null && Tool != null && Tool.Name != null?  $"{SKU} - {Tool.Name}": "";

    }
}
