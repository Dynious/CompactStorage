package com.workshop.compactchests.block;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.workshop.compactchests.tileentity.TileEntityTripleChest;

/**
 * Created by Toby on 19/08/2014.
 */
public class BlockTripleChest extends BlockChest
{
    public BlockTripleChest()
    {
        super(1);

        setBlockName("triplechest");
        setHardness(1f);
        setResistance(1f);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int dim)
    {
        return new TileEntityTripleChest();
    }
}
