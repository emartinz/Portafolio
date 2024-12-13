namespace ToolWorkshop.Utilities
{
    public static class Utils
    {
        public static async Task<byte[]> FindImageAsync(string folder, string name)
        {
            byte[]? imageBytes = null;
            // Verifica si el archivo de imagen existe en la carpeta definida
            string imagePath = Path.Combine(Environment.CurrentDirectory, "wwwroot", "images", folder, name);

            if (File.Exists(imagePath))
            {
                // Lee la imagen como un arreglo de bytes
                imageBytes = await File.ReadAllBytesAsync(imagePath);
            }

            return imageBytes;
        }

        public static async Task<byte[]> SaveImageAndGetDataAsync(IFormFile imageFile, string folderName)
        {
            if (imageFile == null)
            {
                return null;
            }

            // Obtener el directorio base para almacenar la imagen
            string basePath = Path.Combine(Environment.CurrentDirectory, "wwwroot", "images", folderName);

            // Crear el directorio si no existe
            if (!Directory.Exists(basePath))
            {
                Directory.CreateDirectory(basePath);
            }

            // Generar un nombre único para el archivo para evitar colisiones de nombres
            string fileName = Path.GetFileName(imageFile.FileName);
            string filePath = Path.Combine(basePath, fileName);

            // Guardar el archivo en el sistema local
            using (var stream = new FileStream(filePath, FileMode.Create))
            {
                await imageFile.CopyToAsync(stream);
            }

            // Leer los datos del archivo en un arreglo de bytes y devolverlo
            return await File.ReadAllBytesAsync(filePath);
        }

        public static async Task<bool> DeleteImageAsync(string imageName, string folderName)
        {
            if (string.IsNullOrEmpty(imageName) || string.IsNullOrEmpty(folderName))
            {
                return false;
            }

            string basePath = Path.Combine(Environment.CurrentDirectory, "wwwroot", "images", folderName);
            string filePath = Path.Combine(basePath, imageName);

            try
            {
                if (File.Exists(filePath))
                {
                    File.Delete(filePath);
                    return true;
                }
                return false; // La imagen no existe
            }
            catch (IOException ex)
            {
                Console.WriteLine($"Error al borrar la imagen: {ex.Message}");
                return false;
            }
        }
    }
}
