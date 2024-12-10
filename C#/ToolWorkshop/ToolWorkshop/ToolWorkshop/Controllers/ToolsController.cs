﻿using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using ToolWorkshop.Data;
using ToolWorkshop.Data.Entities;
using ToolWorkshop.Helpers;
using ToolWorkshop.Models;
using ToolWorkshop.Utilities;
using Vereyon.Web;
using static ToolWorkshop.Helpers.ModalHelper;

namespace ToolWorkshop.Controllers
{
    [Authorize(Roles = "Admin")]
    public class ToolsController : Controller
    {
        private readonly DataContext _context;
        private readonly ICombosHelper _combosHelper;
         private readonly IFlashMessage _flashMessage;

        public ToolsController(DataContext context, ICombosHelper combosHelper, IFlashMessage flashMessage)
        {
            _context = context;
            _combosHelper = combosHelper;
            _flashMessage = flashMessage;
        }

        public async Task<IActionResult> Index()
        {
            return View(await _context.Tools
                .Include(p => p.ToolImages)
                .Include(p => p.ToolCatalog)
                .Include(p => p.ToolCategories)
                .ThenInclude(pc => pc.Category)
                .ToListAsync());
        }

        [NoDirectAccess]
        public async Task<IActionResult> Create()
        {
            ToolViewModel model = new()
            {
                Categories = await _combosHelper.GetComboCategoriesAsync(),
            };

            return View(model);
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Create(ToolViewModel model)
        {
            if (ModelState.IsValid)
            {
                
                Tool tool = new()
                {
                    Description = model.Description,
                    Name = model.Name,
                    EAN = model.EAN,
                    ToolCatalog = model.ToolCatalog
                };

                tool.ToolCategories = new List<ToolCategory>()
                {
                     new ToolCategory
                     {
                        Category = await _context.Categories.FindAsync(model.CategoryId)
                     }
                };

                try
                {
                    _context.Add(tool);
                    await _context.SaveChangesAsync();
                    _flashMessage.Confirmation("Registro creado.");

                    return Json(new
                    {
                        isValid = true,
                        html = ModalHelper.RenderRazorViewToString(this, "_ViewAllProducts", _context.Tools
                        .Include(p => p.ToolImages)
                        .Include(p => p.ToolCategories)
                        .ThenInclude(pc => pc.Category).ToList())
                    });
                }
                catch (DbUpdateException dbUpdateException)
                {
                    if (dbUpdateException.InnerException.Message.Contains("duplicate"))
                    {
                        _flashMessage.Danger("Ya existe una herramienta con el mismo nombre.");
                    }
                    else
                    {
                        _flashMessage.Danger(dbUpdateException.InnerException.Message);
                    }
                }
                catch (Exception exception)
                {
                    _flashMessage.Danger(exception.Message);
                }
            }

            model.Categories = await _combosHelper.GetComboCategoriesAsync();
            return Json(new { isValid = false, html = ModalHelper.RenderRazorViewToString(this, "Create", model) });
        }

        [NoDirectAccess]

        public async Task<IActionResult> Edit(int id)
        {

            Tool tool= await _context.Tools.Include(p => p.ToolImages)
                .Include(p => p.ToolCategories)
                .ThenInclude(pc => pc.Category)
                .FirstOrDefaultAsync(p => p.Id == id);
            if (tool == null)
            {
                return NotFound();
            }

            ToolViewModel model = new()
            {
                Description = tool.Description,
                Id = tool.Id,
                Name = tool.Name,
                EAN = tool.EAN,
                ToolCatalog = tool.ToolCatalog,
                ToolImages = tool.ToolImages,
                ToolCategories = tool.ToolCategories
            };

            tool.ToolCategories = new List<ToolCategory>()
            {
                new ToolCategory
                {
                    Category = await _context.Categories.FindAsync(model.CategoryId)
                }
            };

            model.Categories = await _combosHelper.GetComboCategoriesAsync();
            return View(model);
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Edit(int id, ToolViewModel model)
        {
            if (id != model.Id)
            {
                return NotFound();
            }

            try
            {
                Tool tool= await _context.Tools.FindAsync(model.Id);
                tool.Description = model.Description;
                tool.Name = model.Name;
                tool.EAN = model.EAN;
                tool.ToolCatalog = model.ToolCatalog;
                _context.Update(tool);
                await _context.SaveChangesAsync();
                return RedirectToAction(nameof(Index));
            }
            catch (DbUpdateException dbUpdateException)
            {
                if (dbUpdateException.InnerException.Message.Contains("duplicate"))
                {
                    _flashMessage.Danger("Ya existe una herramienta con el mismo nombre.");
                }
                else
                {
                    _flashMessage.Danger(dbUpdateException.InnerException.Message);
                }
            }
            catch (Exception exception)
            {
                _flashMessage.Danger(exception.Message);
            }

            return View(model);
        }
        public async Task<IActionResult> Details(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            Tool tool= await _context.Tools
                .Include(p => p.ToolImages)
                .Include(p => p.ToolCategories)
                .ThenInclude(pc => pc.Category)
                .FirstOrDefaultAsync(p => p.Id == id);
            if (tool == null)
            {
                return NotFound();
            }

            return View(tool);
        }
        public async Task<IActionResult> AddImage(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            Tool tool= await _context.Tools.FindAsync(id);
            if (tool== null)
            {
                return NotFound();
            }

            AddToolImageViewModel model = new()
            {
                ToolId = tool.Id,
            };

            return View(model);
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> AddImage(AddToolImageViewModel model)
        {
            if (ModelState.IsValid)
            {
                Tool tool = await _context.Tools.FindAsync(model.ToolId);
                ToolImage productImage = new()
                {
                    Tool= tool,
                    ImageData = await Utils.SaveImageAndGetDataAsync(model.ImageFile, "products"),
                };

                try
                {
                    _context.Add(productImage);
                    await _context.SaveChangesAsync();
                    return RedirectToAction(nameof(Details), new { Id = tool.Id });
                }
                catch (Exception exception)
                {
                    _flashMessage.Danger(exception.Message);
                }
            }

            return View(model);
        }
        public async Task<IActionResult> DeleteImage(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            ToolImage toolImage = await _context.ToolImages
                .Include(pi => pi.Tool)
                .FirstOrDefaultAsync(pi => pi.Id == id);

            if (toolImage == null)
            {
                return NotFound();
            }

            //await _blobHelper.DeleteBlobAsync(toolImage.ImageId, "products");
            _context.ToolImages.Remove(toolImage);
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Details), new { Id = toolImage.Tool.Id });
        }

        public async Task<IActionResult> AddCategory(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            Tool tool= await _context.Tools
                .Include(p => p.ToolCategories)
                .ThenInclude(pc =>pc.Category)
                .FirstOrDefaultAsync(p => p.Id == id);
            if (tool == null)
            {
                return NotFound();
            }

            List<Category> categories = tool.ToolCategories.Select(pc => new Category 
            {
                Id = pc.Category.Id,
                Name = pc.Category.Name,
            }).ToList();

            AddCategoryToolViewModel model = new()
            {
                ToolId = tool.Id,
                Categories = await _combosHelper.GetComboCategoriesAsync(categories),
            };

            return View(model);
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> AddCategory(AddCategoryToolViewModel model)
        { 
            Tool tool = await _context.Tools
                                .Include(p => p.ToolCategories)
                                .ThenInclude(pc => pc.Category)
                                .FirstOrDefaultAsync(p => p.Id == model.ToolId);
            if (ModelState.IsValid)
            {
               
                ToolCategory toolCategory = new()
                {
                    Category = await _context.Categories.FindAsync(model.CategoryId),
                    Tool = tool,
                };

                try
                {
                    _context.Add(toolCategory);
                    await _context.SaveChangesAsync();
                    return RedirectToAction(nameof(Details), new { Id = tool.Id });
                }
                catch (Exception exception)
                {
                    _flashMessage.Danger(exception.Message);
                }
            }
   
           
                List<Category> categories = tool.ToolCategories.Select(pc => new Category
                {
                    Id = pc.Category.Id,
                    Name = pc.Category.Name,
                }).ToList();

            model.Categories = await _combosHelper.GetComboCategoriesAsync(categories);
            return View(model);
        }


        public async Task<IActionResult> DeleteCategory(int? id)
        {
            if (id == null)
            {
                return NotFound();
            }

            ToolCategory productCategory = await _context.ToolCategories
                .Include(pc => pc.Tool)
                .FirstOrDefaultAsync(pc => pc.Id == id);
            if (productCategory == null)
            {
                return NotFound();
            }

            _context.ToolCategories.Remove(productCategory);
            await _context.SaveChangesAsync();
            return RedirectToAction(nameof(Details), new { Id = productCategory.Tool.Id });
        }

        [NoDirectAccess]
        public async Task<IActionResult> Delete(int id)
        {
            Tool tool = await _context.Tools
                .Include(p => p.ToolCategories)
                .Include(p => p.ToolImages)
                .FirstOrDefaultAsync(p => p.Id == id);
            if (tool == null)
            {
                return NotFound();
            }

            //foreach (ToolImage productImage in tool.ToolImages)
            //{
            //    await _blobHelper.DeleteBlobAsync(productImage.ImageId, "products");
            //}

            _context.Tools.Remove(tool);
            await _context.SaveChangesAsync();
            _flashMessage.Info("Registro borrado.");
            return RedirectToAction(nameof(Index));
        }

    }
}
