-----------------------------------------------------------------------
--          GtkAda - Ada95 binding for the Gimp Toolkit              --
--                                                                   --
--                     Copyright (C) 1998-1999                       --
--        Emmanuel Briot, Joel Brobecker and Arnaud Charlet          --
--                                                                   --
-- This library is free software; you can redistribute it and/or     --
-- modify it under the terms of the GNU General Public               --
-- License as published by the Free Software Foundation; either      --
-- version 2 of the License, or (at your option) any later version.  --
--                                                                   --
-- This library is distributed in the hope that it will be useful,   --
-- but WITHOUT ANY WARRANTY; without even the implied warranty of    --
-- MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU --
-- General Public License for more details.                          --
--                                                                   --
-- You should have received a copy of the GNU General Public         --
-- License along with this library; if not, write to the             --
-- Free Software Foundation, Inc., 59 Temple Place - Suite 330,      --
-- Boston, MA 02111-1307, USA.                                       --
--                                                                   --
--
--
--
--
--
--
--

--******************************************************************
--******************************************************************
-- MODIFICACIONES (Agosto 2021):   Migración de GtkAda2 a GktAda3
--   Gdk.Drawable se sustituye por Cairo
--   Gdk.Pixmap se sustituye por Gdk.Pixbuf
--******************************************************************
--******************************************************************




with Glib;             use Glib;
with Gtk.Enums;        use Gtk.Enums;
with Gtk.Style;        use Gtk.Style;
with Cairo;             use Cairo;
with Gdk.Pixbuf;       use Gdk.Pixbuf;
--with Gdk.Drawable;     use Gdk.Drawable;
with Gtkada.Style;      use Gtkada.Style;
with Gdk.Color;
with Gdk.Cairo;
with Gdk.Event;        use Gdk.Event;
with Gdk.Window;       use Gdk.Window;
with Gdk.Rectangle;    use Gdk.Rectangle;
with Gtk.Drawing_Area; use Gtk.Drawing_Area;
with Gtk.Handlers;     use Gtk.Handlers;
pragma Elaborate_All (Gtk.Handlers);


package body Double_Buffer is

   package Event_Cb is new Gtk.Handlers.Return_Callback
     (Widget_Type => Gtk_Double_Buffer_Record,
      Return_Type => Boolean);
   package Void_Cb is new Gtk.Handlers.Callback
     (Widget_Type => Gtk_Double_Buffer_Record);

   function Configure (Buffer        : access Gtk_Double_Buffer_Record'Class;
                       Event         : Gdk.Event.Gdk_Event)
                      return Boolean;
   --  Callback when the buffer is resized

   function Expose (Buffer        : access Gtk_Double_Buffer_Record'Class;
                    Event         : Gdk.Event.Gdk_Event)
                   return Boolean;
   --  Callback when the window needs to redraw itself on the screen

   function Create_Internal_Pixmap
     (Buffer : access Gtk_Double_Buffer_Record'Class)
     return Gdk.Pixbuf.Gdk_Pixbuf;
   --  Create one of the internal pixmaps used by the buffer, with the
   --  correct size.

   procedure Destroy_Cb (Buffer : access Gtk_Double_Buffer_Record'Class);
   --  Called when the widget is destroyed, to free up some memory

   -------------
   -- Gtk_New --
   -------------

   procedure Gtk_New (Buffer : out Gtk_Double_Buffer) is
   begin
      Buffer := new Gtk_Double_Buffer_Record;
      Double_Buffer.Initialize (Buffer);
   end Gtk_New;

   ----------------
   -- Initialize --
   ----------------

   procedure Initialize (Buffer : Gtk_Double_Buffer) is
   begin

      Gtk.Drawing_Area.Initialize (Buffer);

      --  Note: the pixmap is created in the resize routine
      --  and the GC is created in the Realize_Cb routine.

      --  Connect the signals
      Event_Cb.Connect (Buffer, "configure_event",
                        Event_Cb.To_Marshaller (Configure'Access));
      Event_Cb.Connect (Buffer, "draw",
                        Event_Cb.To_Marshaller (Expose'Access));
      --Event_Cb.Connect (Buffer, "expose_event",
      --                  Event_Cb.To_Marshaller (Expose'Access));
      Void_Cb.Connect (Buffer, "destroy",
                       Void_Cb.To_Marshaller (Destroy_Cb'Access));

   end Initialize;

   ----------------------------
   -- Create_Internal_Pixmap --
   ----------------------------

   function Create_Internal_Pixmap
     (Buffer : access Gtk_Double_Buffer_Record'Class)
     return Gdk.Pixbuf.Gdk_Pixbuf
   is
   begin
      return Gdk.Pixbuf.Gdk_New(Colorspace_RGB,
                                     Width => CANVAS_WIDTH, Height => CANVAS_HEIGHT);
--      Gdk.Pixmap.Gdk_New (Pix,
--                          Get_Window (Buffer),
--                          Gint (Get_Allocation_Width (Buffer)),
--                          Gint (Get_Allocation_Height (Buffer)));
      --return Pix;
   end Create_Internal_Pixmap;

   ---------------
   -- Configure --
   ---------------

   function Configure (Buffer        : access Gtk_Double_Buffer_Record'Class;
                       Event         : Gdk.Event.Gdk_Event)
                      return Boolean
   is
      Old_Pixmap : Gdk.Pixbuf.Gdk_Pixbuf := Buffer.Pixmap;
      Old_Triple : Gdk.Pixbuf.Gdk_Pixbuf := Buffer.Triple_Buffer;
      Old_Pixmap_cairo_context : Cairo.Cairo_Context;
      Old_Triple_cairo_context : Cairo.Cairo_Context;
      use type Cairo.Cairo_Context;

   begin
      Old_Pixmap_cairo_context := Gdk.Cairo.Create(Get_Window(Buffer));

      Buffer.Pixmap := Create_Internal_Pixmap (Buffer);


      if Buffer.Use_Triple_Buffer then
         Old_Triple_cairo_context := Gdk.Cairo.Create(Get_Window(Buffer));
         Buffer.Triple_Buffer := Create_Internal_Pixmap (Buffer);
      end if;


      if Old_Pixmap /= Gdk.Pixbuf.Null_Pixbuf then

         --  If Back_Store is True, then we want to keep the content
         --  of the old pixmap, and thus we copy it to the new one.

         if Buffer.Back_Store then
            declare
               Old_Width  : Gint;
               Old_Height : Gint;

            begin
               Old_Width := Get_Width(Old_Pixmap);
               Old_Height := Get_Height(Old_Pixmap);

               Draw_Rectangle (Old_Pixmap_cairo_context,
                          Gdk.Color.Null_Color,
                          Filled => True,
                          X => 0,  Y => 0,
                          Width => Old_Width, Height => Old_Height);

               --Gdk.Drawable.Draw_Rectangle
               --  (Buffer.Pixmap,
               --   Get_Background_GC (Get_Style (Buffer),
               --                      State_Normal),
               --   Filled => True,
               --   X      => 0,
               --   Y      => 0,
               --   Width  => Gdk.Event.Get_Width (Event),
                --  Height => Gdk.Event.Get_Height (Event));

               Gdk.Cairo.Set_Source_Pixbuf(Old_Pixmap_cairo_context, Old_Pixmap, 0.0, 0.0);

               --Gdk.Drawable.Draw_Drawable
               --  (Buffer.Pixmap,
               --   Get_Foreground_GC (Get_Style (Buffer), State_Normal),
               --   Src      => Old_Pixmap,
               --   Xsrc     => 0,
               --   Ysrc     => 0,
               --   Xdest    => 0,
               --   Ydest    => 0,
               --   Width    => Old_Width,
                --  Height   => Old_Height);
            end;
         end if;

         Gdk.Pixbuf.Unref(Old_Pixmap);
      end if;

      if Old_Triple /= Gdk.Pixbuf.Null_Pixbuf then

         --  If Back_Store is True, then we want to keep the content
         --  of the old pixmap, and thus we copy it to the new one.

         if Buffer.Back_Store then
            declare
               Old_Width  : Gint;
               Old_Height : Gint;

            begin
               Old_Width := Get_Width(Old_Triple);
               Old_Height := Get_Height(Old_Triple);

               Draw_Rectangle (Old_Triple_cairo_context,
                          Gdk.Color.Null_Color,
                          Filled => True,
                          X => 0,  Y => 0,
                          Width => Old_Width, Height => Old_Height);


             --  Gdk.Drawable.Draw_Rectangle
             --    (Buffer.Triple_Buffer,
             --     Get_Background_GC (Get_Style (Buffer),
             --                        State_Normal),
             --     Filled => True,
             --     X      => 0,
             --     Y      => 0,
             --     Width  => Gdk.Event.Get_Width (Event),
             --     Height => Gdk.Event.Get_Height (Event));

               Gdk.Cairo.Set_Source_Pixbuf(Old_Triple_cairo_context, Old_Triple, 0.0, 0.0);


               --Gdk.Drawable.Draw_Drawable
               --  (Buffer.Triple_Buffer,
               --   Get_Foreground_GC (Get_Style (Buffer), State_Normal),
               --   Src      => Old_Triple,
               --   Xdest    => 0,
                --  Ydest    => 0,
                --  Xsrc     => 0,
                --  Ysrc     => 0,
                --  Width    => Old_Width,
                --  Height   => Old_Height);
            end;
         end if;

         Gdk.Pixbuf.Unref (Old_Triple);
      end if;

      return True;
   end Configure;

   ----------------
   -- Destroy_Cb --
   ----------------

   procedure Destroy_Cb (Buffer : access Gtk_Double_Buffer_Record'Class) is
      --use type Gdk.Pixmap.Gdk_Pixmap;
      use type Gdk.Pixbuf.Gdk_Pixbuf;
   begin
      if Buffer.Pixmap /= Gdk.Pixbuf.Null_Pixbuf then
         Gdk.Pixbuf.Unref (Buffer.Pixmap);
      end if;

      if Buffer.Triple_Buffer /= Gdk.Pixbuf.Null_Pixbuf then
         Gdk.Pixbuf.Unref (Buffer.Triple_Buffer);
      end if;
   end Destroy_Cb;

   ------------
   -- Expose --
   ------------

   function Expose (Buffer        : access Gtk_Double_Buffer_Record'Class;
                    Event         : Gdk.Event.Gdk_Event)
                   return Boolean
   is
      Area : Gdk.Rectangle.Gdk_Rectangle := Event.Expose.Area;
      --Area : Gdk.Rectangle.Gdk_Rectangle := Gdk.Event.Get_Area (Event);

      Old_Pixmap : Gdk.Pixbuf.Gdk_Pixbuf := Buffer.Pixmap;
      Old_Triple : Gdk.Pixbuf.Gdk_Pixbuf := Buffer.Triple_Buffer;
      Old_Pixmap_cairo_context : Cairo.Cairo_Context;
      Old_Triple_cairo_context : Cairo.Cairo_Context;

   begin

      Old_Pixmap_cairo_context := Gdk.Cairo.Create(Get_Window(Buffer));


      if Buffer.Use_Triple_Buffer then

         Old_Triple_cairo_context := Gdk.Cairo.Create(Get_Window(Buffer));

         --  If the event was generated manually, we need to update
         --  the Triple_Buffer with the value of the double_buffer,
         --  unless of course the buffer is frozen.

         if Gdk.Event.Events_Pending then
         --if Gdk.Event.Get_Send_Event (Event) then
            if Buffer.Is_Frozen then
               Buffer.Should_Update_On_Screen := True;
            else
               Gdk.Cairo.Set_Source_Pixbuf(Old_Triple_cairo_context, Old_Pixmap, 0.0, 0.0);

               --Gdk.Drawable.Draw_Drawable
               --  (Buffer.Triple_Buffer,
               --   Get_Foreground_GC (Get_Style (Buffer), State_Normal),
               --   Src      => Buffer.Pixmap,
               --   Xdest    => 0,
               --   Ydest    => 0,
               --   Xsrc     => 0,
               --   Ysrc     => 0,
               --   Width    => Gint (Get_Allocation_Width (Buffer)),
               --   Height   => Gint (Get_Allocation_Height (Buffer)));
            end if;
         end if;

         Gdk.Cairo.Set_Source_Pixbuf(Old_Pixmap_cairo_context, Old_Triple, 0.0, 0.0);

         --  Copy the triple buffer to the screen
         --Gdk.Drawable.Draw_Drawable
         --  (Get_Window (Buffer),
         --   Get_Foreground_GC (Get_Style (Buffer), State_Normal),
         --   Src      => Buffer.Triple_Buffer,
         --   Xdest    => Area.X,
          --  Ydest    => Area.Y,
          --  Xsrc     => Area.X,
          --  Ysrc     => Area.Y,
          --  Width    => Gint (Area.Width),
          --  Height   => Gint (Area.Height));

      elsif not Buffer.Is_Frozen then
          Gdk.Cairo.Set_Source_Pixbuf(Old_Pixmap_cairo_context, Old_Pixmap, 0.0, 0.0);

         --  Copy the double buffer to the screen
         --Gdk.Drawable.Draw_Drawable
         --  (Get_Window (Buffer),
         --   Get_Foreground_GC (Get_Style (Buffer), State_Normal),
         --   Src      => Buffer.Pixmap,
         --   Xdest    => Area.X,
         --   Ydest    => Area.Y,
         --   Xsrc     => Area.X,
         --   Ysrc     => Area.Y,
         --   Width    => Gint (Area.Width),
         --  Height   => Gint (Area.Height));

      else
         Buffer.Should_Update_On_Screen := True;
      end if;


      return False;
   end Expose;

   ----------------
   -- Get_Pixmap --
   ----------------

   function Get_Pixmap (Buffer : access Gtk_Double_Buffer_Record)
                        return Cairo.Cairo_Context is --Gdk.Drawable.Gdk_Drawable is
      ventana : Gdk.Gdk_Window;
      context : Cairo.Cairo_Context;
   begin
      ventana := Get_Window(Gtk_Drawing_Area(Buffer));
      context := Gdk.Cairo.Create(ventana);

      Gdk.Cairo.Set_Source_Pixbuf(context, Buffer.Pixmap, 0.0, 0.0);

      return context;
      --return Gdk.Drawable.Gdk_Drawable (Buffer.Pixmap);
   end Get_Pixmap;

   --------------------
   -- Set_Back_Store --
   --------------------

   procedure Set_Back_Store (Buffer : access Gtk_Double_Buffer_Record;
                             Back_Store : Boolean := True) is
   begin
      Buffer.Back_Store := Back_Store;
   end Set_Back_Store;

   ------------
   -- Freeze --
   ------------

   procedure Freeze (Buffer : access Gtk_Double_Buffer_Record) is
   begin
      Buffer.Is_Frozen := True;
   end Freeze;

   ----------
   -- Thaw --
   ----------

   procedure Thaw (Buffer : access Gtk_Double_Buffer_Record) is
      context : Cairo.Cairo_Context :=  Gdk.Cairo.Create(Get_Window(Buffer));
      Pixmap : Gdk.Pixbuf.Gdk_Pixbuf := Buffer.Pixmap;
   begin
      Buffer.Is_Frozen := False;

      if Buffer.Should_Update_On_Screen then
         Gdk.Cairo.Set_Source_Pixbuf(context, Pixmap, 0.0, 0.0);

         --Draw (Buffer);
      end if;
   end Thaw;

   -----------------------
   -- Set_Triple_Buffer --
   -----------------------

   procedure Set_Triple_Buffer (Buffer : access Gtk_Double_Buffer_Record;
                                Use_Triple_Buffer : Boolean := True) is
      context : Cairo.Cairo_Context := Gdk.Cairo.Create(Get_Window(Buffer));

      use type Gdk.Gdk_Window;
   begin
      Buffer.Use_Triple_Buffer := Use_Triple_Buffer;

      --  If we do not want triple_buffer but already have one, delete it
      if not Use_Triple_Buffer
        and then Buffer.Triple_Buffer /= null
      then
         Gdk.Pixbuf.Unref (Buffer.Triple_Buffer);
      end if;

      --  If we do want a triple buffer, create the pixmap

      if Use_Triple_Buffer then
         Buffer.Triple_Buffer := Create_Internal_Pixmap (Buffer);

         Gdk.Cairo.Set_Source_Pixbuf(context, Buffer.Triple_Buffer, 0.0, 0.0);


         --  Copy the current value of the pixmap in it
         --Gdk.Drawable.Draw_Drawable
         --  (Buffer.Triple_Buffer,
         --   Get_Foreground_GC (Get_Style (Buffer), State_Normal),
         --   Src      => Buffer.Pixmap,
         --   Xdest    => 0,
         --   Ydest    => 0,
         --   Xsrc     => 0,
          --  Ysrc     => 0,
         --   Width    => Gint (Get_Allocation_Width (Buffer)),
         --   Height   => Gint (Get_Allocation_Height (Buffer)));
      end if;
   end Set_Triple_Buffer;

end Double_Buffer;
