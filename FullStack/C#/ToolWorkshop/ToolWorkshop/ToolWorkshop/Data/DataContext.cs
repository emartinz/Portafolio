﻿using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using ToolWorkshop.Data.Entities;

namespace ToolWorkshop.Data
{
    public class DataContext : IdentityDbContext<User>

    {
        public DataContext(DbContextOptions<DataContext> options) : base(options)
        {
        }

        public DbSet<Country> Countries { get; set; }
        public DbSet<State> States { get; set; }
        public DbSet<City> Cities { get; set; }
        public DbSet<Warehouse> Warehouses { get; set; }
        public DbSet<Planogram> Planograms { get; set; } 
        public DbSet<Category> Categories { get; set; }
        public DbSet<Tool> Tools { get; set; }
        public DbSet<ToolCategory> ToolCategories { get; set; }
        public DbSet<ToolImage> ToolImages { get; set; }
        public DbSet<Catalog> Catalogs { get; set; }
        public DbSet<Movement> Movements { get; set; }
        public DbSet<Movement_Detail> Movement_Details { get; set; }
        public DbSet<Temporal_Movement> Temporal_Movements { get; set; }
        
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
            modelBuilder.Entity<Category>().HasIndex(c => c.Name).IsUnique();
            modelBuilder.Entity<Country>().HasIndex(c => c.Name).IsUnique();
            modelBuilder.Entity<State>().HasIndex("Name", "CountryId").IsUnique();
            modelBuilder.Entity<City>().HasIndex("Name", "StateId").IsUnique();
            modelBuilder.Entity<Tool>().HasIndex(c => c.Name).IsUnique();
            modelBuilder.Entity<ToolCategory>().HasIndex("ToolId", "CategoryId").IsUnique();
            modelBuilder.Entity<Catalog>().Property(c => c.Status).HasConversion<string>().HasMaxLength(30);
            modelBuilder.Entity<Movement>().Property(m => m.Status).HasConversion<string>().HasMaxLength(30);
            modelBuilder.Entity<Temporal_Movement>().Property(tm => tm.Status).HasConversion<string>().HasMaxLength(30);
        }
    }
}