/*
  Warnings:

  - Added the required column `lokasi` to the `Product` table without a default value. This is not possible if the table is not empty.

*/
-- AlterTable
ALTER TABLE `Product` ADD COLUMN `description` VARCHAR(191) NULL,
    ADD COLUMN `lokasi` VARCHAR(191) NOT NULL,
    ADD COLUMN `rating` BOOLEAN NOT NULL DEFAULT false,
    MODIFY `name` VARCHAR(191) NULL;
