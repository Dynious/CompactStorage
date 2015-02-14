package com.workshop.compactchests.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.workshop.compactchests.tileentity.TileEntityQuadrupleChest;

/**
 * Created by Toby on 19/08/2014.
 */
public class BlockQuadrupleChest extends BlockChest
{
    public BlockQuadrupleChest()
    {
        super(2);

        setBlockName("quadruplechest");
        setHardness(1f);
        setResistance(1f);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int dim)
    {
        return new TileEntityQuadrupleChest();
    }
}
