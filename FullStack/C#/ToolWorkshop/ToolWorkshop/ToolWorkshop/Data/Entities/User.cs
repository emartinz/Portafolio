using Microsoft.AspNetCore.Identity;
using System.ComponentModel.DataAnnotations;
using ToolWorkshop.Enums;

namespace ToolWorkshop.Data.Entities
{
    public class User : IdentityUser
    {
        //public int Id { get; set; }

        [Display(Name = "Nombres")]
        [MaxLength(50, ErrorMessage = "El campo {0} debe tener máximo {1} caractéres.")]
        [Required(ErrorMessage = "El Campo {0} es obligatorio.")]
        public string FirstName { get; set; }

        [Display(Name = "Apellidos")]
        [MaxLength(50, ErrorMessage = "El campo {0} debe tener máximo {1} caractéres.")]
        [Required(ErrorMessage = "El Campo {0} es obligatorio.")]
        public string LastName { get; set; }

        [Display(Name = "Ciudad")]
        public City City { get; set; }

        [Display(Name = "Dirección")]
        [MaxLength(200, ErrorMessage = "El campo {0} debe tener máximo {1} caractéres.")]
        [Required(ErrorMessage = "El campo {0} es obligatorio.")]
        public string Address { get; set; }

        [Display(Name = "Correo")]
        [MaxLength(64, ErrorMessage = "El campo {0} debe tener máximo {1} caractéres.")]
        [Required(ErrorMessage = "El Campo {0} es obligatorio.")]
        public string Email { get; set; }

        [Display(Name = "Tipo de Documento")]
        [Required(ErrorMessage = "El Campo {0} es obligatorio.")]
        public DocumentType DocumentType { get; set; }

        [Display(Name = "Numero de Documento")]
        [MaxLength(20, ErrorMessage = "El campo {0} debe tener máximo {1} caractéres.")]
        [Required(ErrorMessage = "El Campo {0} es obligatorio.")]
        public String Document { get; set; }

        [Display(Name = "Estado")]
        [Required(ErrorMessage = "El Campo {0} es obligatorio.")]
        public UserStatus Status { get; set; }

        [Display(Name = "Foto")]
        public byte[] ImageData { get; set; }

        [Display(Name = "Tipo de Usuario")]
        public UserType UserType { get; set; }

        public ICollection<Role>? Roles;
        public ICollection<Temporal_Movement>? Temporal_Movements;

        [Display(Name = "Usuario")]
        public string FullName => $"{FirstName} {LastName}";

        [Display(Name = "Usuario")]
        public string FullNameWithDocument => $"{FirstName} {LastName} - {Document}";
    }
}
