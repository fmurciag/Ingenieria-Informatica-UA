pragma Warnings (Off);
pragma Ada_95;
pragma Source_File_Name (ada_main, Spec_File_Name => "b__main.ads");
pragma Source_File_Name (ada_main, Body_File_Name => "b__main.adb");
pragma Suppress (Overflow_Check);

with System.Restrictions;
with Ada.Exceptions;

package body ada_main is

   E068 : Short_Integer; pragma Import (Ada, E068, "system__os_lib_E");
   E016 : Short_Integer; pragma Import (Ada, E016, "ada__exceptions_E");
   E012 : Short_Integer; pragma Import (Ada, E012, "system__soft_links_E");
   E010 : Short_Integer; pragma Import (Ada, E010, "system__exception_table_E");
   E033 : Short_Integer; pragma Import (Ada, E033, "ada__containers_E");
   E063 : Short_Integer; pragma Import (Ada, E063, "ada__io_exceptions_E");
   E007 : Short_Integer; pragma Import (Ada, E007, "ada__strings_E");
   E051 : Short_Integer; pragma Import (Ada, E051, "ada__strings__maps_E");
   E055 : Short_Integer; pragma Import (Ada, E055, "ada__strings__maps__constants_E");
   E038 : Short_Integer; pragma Import (Ada, E038, "interfaces__c_E");
   E019 : Short_Integer; pragma Import (Ada, E019, "system__exceptions_E");
   E074 : Short_Integer; pragma Import (Ada, E074, "system__object_reader_E");
   E045 : Short_Integer; pragma Import (Ada, E045, "system__dwarf_lines_E");
   E090 : Short_Integer; pragma Import (Ada, E090, "system__soft_links__initialize_E");
   E032 : Short_Integer; pragma Import (Ada, E032, "system__traceback__symbolic_E");
   E429 : Short_Integer; pragma Import (Ada, E429, "ada__assertions_E");
   E113 : Short_Integer; pragma Import (Ada, E113, "ada__numerics_E");
   E094 : Short_Integer; pragma Import (Ada, E094, "ada__strings__utf_encoding_E");
   E100 : Short_Integer; pragma Import (Ada, E100, "ada__tags_E");
   E005 : Short_Integer; pragma Import (Ada, E005, "ada__strings__text_buffers_E");
   E213 : Short_Integer; pragma Import (Ada, E213, "gnat_E");
   E187 : Short_Integer; pragma Import (Ada, E187, "interfaces__c__strings_E");
   E117 : Short_Integer; pragma Import (Ada, E117, "ada__streams_E");
   E129 : Short_Integer; pragma Import (Ada, E129, "system__file_control_block_E");
   E128 : Short_Integer; pragma Import (Ada, E128, "system__finalization_root_E");
   E126 : Short_Integer; pragma Import (Ada, E126, "ada__finalization_E");
   E125 : Short_Integer; pragma Import (Ada, E125, "system__file_io_E");
   E191 : Short_Integer; pragma Import (Ada, E191, "system__storage_pools_E");
   E189 : Short_Integer; pragma Import (Ada, E189, "system__finalization_masters_E");
   E208 : Short_Integer; pragma Import (Ada, E208, "system__storage_pools__subpools_E");
   E150 : Short_Integer; pragma Import (Ada, E150, "system__task_info_E");
   E144 : Short_Integer; pragma Import (Ada, E144, "system__task_primitives__operations_E");
   E110 : Short_Integer; pragma Import (Ada, E110, "ada__calendar_E");
   E108 : Short_Integer; pragma Import (Ada, E108, "ada__calendar__delays_E");
   E494 : Short_Integer; pragma Import (Ada, E494, "ada__real_time_E");
   E115 : Short_Integer; pragma Import (Ada, E115, "ada__text_io_E");
   E193 : Short_Integer; pragma Import (Ada, E193, "system__pool_global_E");
   E498 : Short_Integer; pragma Import (Ada, E498, "system__random_seed_E");
   E167 : Short_Integer; pragma Import (Ada, E167, "system__tasking__initialization_E");
   E133 : Short_Integer; pragma Import (Ada, E133, "system__tasking__protected_objects_E");
   E163 : Short_Integer; pragma Import (Ada, E163, "system__tasking__protected_objects__entries_E");
   E175 : Short_Integer; pragma Import (Ada, E175, "system__tasking__queuing_E");
   E492 : Short_Integer; pragma Import (Ada, E492, "system__tasking__stages_E");
   E182 : Short_Integer; pragma Import (Ada, E182, "glib_E");
   E185 : Short_Integer; pragma Import (Ada, E185, "gtkada__types_E");
   E272 : Short_Integer; pragma Import (Ada, E272, "gdk__frame_timings_E");
   E224 : Short_Integer; pragma Import (Ada, E224, "glib__glist_E");
   E254 : Short_Integer; pragma Import (Ada, E254, "gdk__visual_E");
   E226 : Short_Integer; pragma Import (Ada, E226, "glib__gslist_E");
   E453 : Short_Integer; pragma Import (Ada, E453, "glib__poll_E");
   E218 : Short_Integer; pragma Import (Ada, E218, "gtkada__c_E");
   E204 : Short_Integer; pragma Import (Ada, E204, "glib__object_E");
   E206 : Short_Integer; pragma Import (Ada, E206, "glib__type_conversion_hooks_E");
   E220 : Short_Integer; pragma Import (Ada, E220, "glib__types_E");
   E222 : Short_Integer; pragma Import (Ada, E222, "glib__values_E");
   E212 : Short_Integer; pragma Import (Ada, E212, "gtkada__bindings_E");
   E232 : Short_Integer; pragma Import (Ada, E232, "cairo_E");
   E234 : Short_Integer; pragma Import (Ada, E234, "cairo__region_E");
   E241 : Short_Integer; pragma Import (Ada, E241, "gdk__rectangle_E");
   E239 : Short_Integer; pragma Import (Ada, E239, "glib__generic_properties_E");
   E264 : Short_Integer; pragma Import (Ada, E264, "gdk__color_E");
   E244 : Short_Integer; pragma Import (Ada, E244, "gdk__rgba_E");
   E237 : Short_Integer; pragma Import (Ada, E237, "gdk__event_E");
   E369 : Short_Integer; pragma Import (Ada, E369, "glib__key_file_E");
   E256 : Short_Integer; pragma Import (Ada, E256, "glib__properties_E");
   E455 : Short_Integer; pragma Import (Ada, E455, "glib__spawn_E");
   E451 : Short_Integer; pragma Import (Ada, E451, "glib__main_E");
   E331 : Short_Integer; pragma Import (Ada, E331, "glib__string_E");
   E329 : Short_Integer; pragma Import (Ada, E329, "glib__variant_E");
   E327 : Short_Integer; pragma Import (Ada, E327, "glib__g_icon_E");
   E464 : Short_Integer; pragma Import (Ada, E464, "gtk__actionable_E");
   E280 : Short_Integer; pragma Import (Ada, E280, "gtk__builder_E");
   E317 : Short_Integer; pragma Import (Ada, E317, "gtk__buildable_E");
   E343 : Short_Integer; pragma Import (Ada, E343, "gtk__cell_area_context_E");
   E359 : Short_Integer; pragma Import (Ada, E359, "gtk__css_section_E");
   E258 : Short_Integer; pragma Import (Ada, E258, "gtk__enums_E");
   E323 : Short_Integer; pragma Import (Ada, E323, "gtk__orientable_E");
   E371 : Short_Integer; pragma Import (Ada, E371, "gtk__paper_size_E");
   E367 : Short_Integer; pragma Import (Ada, E367, "gtk__page_setup_E");
   E379 : Short_Integer; pragma Import (Ada, E379, "gtk__print_settings_E");
   E288 : Short_Integer; pragma Import (Ada, E288, "gtk__target_entry_E");
   E286 : Short_Integer; pragma Import (Ada, E286, "gtk__target_list_E");
   E293 : Short_Integer; pragma Import (Ada, E293, "pango__enums_E");
   E311 : Short_Integer; pragma Import (Ada, E311, "pango__attributes_E");
   E297 : Short_Integer; pragma Import (Ada, E297, "pango__font_metrics_E");
   E299 : Short_Integer; pragma Import (Ada, E299, "pango__language_E");
   E295 : Short_Integer; pragma Import (Ada, E295, "pango__font_E");
   E385 : Short_Integer; pragma Import (Ada, E385, "gtk__text_attributes_E");
   E387 : Short_Integer; pragma Import (Ada, E387, "gtk__text_tag_E");
   E303 : Short_Integer; pragma Import (Ada, E303, "pango__font_face_E");
   E301 : Short_Integer; pragma Import (Ada, E301, "pango__font_family_E");
   E305 : Short_Integer; pragma Import (Ada, E305, "pango__fontset_E");
   E307 : Short_Integer; pragma Import (Ada, E307, "pango__matrix_E");
   E291 : Short_Integer; pragma Import (Ada, E291, "pango__context_E");
   E375 : Short_Integer; pragma Import (Ada, E375, "pango__font_map_E");
   E313 : Short_Integer; pragma Import (Ada, E313, "pango__tabs_E");
   E309 : Short_Integer; pragma Import (Ada, E309, "pango__layout_E");
   E373 : Short_Integer; pragma Import (Ada, E373, "gtk__print_context_E");
   E252 : Short_Integer; pragma Import (Ada, E252, "gdk__display_E");
   E270 : Short_Integer; pragma Import (Ada, E270, "gdk__frame_clock_E");
   E274 : Short_Integer; pragma Import (Ada, E274, "gdk__pixbuf_E");
   E250 : Short_Integer; pragma Import (Ada, E250, "gdk__screen_E");
   E266 : Short_Integer; pragma Import (Ada, E266, "gdk__device_E");
   E268 : Short_Integer; pragma Import (Ada, E268, "gdk__drag_contexts_E");
   E389 : Short_Integer; pragma Import (Ada, E389, "gdk__window_E");
   E278 : Short_Integer; pragma Import (Ada, E278, "gtk__accel_group_E");
   E321 : Short_Integer; pragma Import (Ada, E321, "gtk__adjustment_E");
   E333 : Short_Integer; pragma Import (Ada, E333, "gtk__cell_editable_E");
   E335 : Short_Integer; pragma Import (Ada, E335, "gtk__editable_E");
   E337 : Short_Integer; pragma Import (Ada, E337, "gtk__entry_buffer_E");
   E355 : Short_Integer; pragma Import (Ada, E355, "gtk__icon_source_E");
   E377 : Short_Integer; pragma Import (Ada, E377, "gtk__print_operation_preview_E");
   E282 : Short_Integer; pragma Import (Ada, E282, "gtk__selection_data_E");
   E284 : Short_Integer; pragma Import (Ada, E284, "gtk__style_E");
   E383 : Short_Integer; pragma Import (Ada, E383, "gtk__text_iter_E");
   E349 : Short_Integer; pragma Import (Ada, E349, "gtk__tree_model_E");
   E262 : Short_Integer; pragma Import (Ada, E262, "gtk__widget_E");
   E347 : Short_Integer; pragma Import (Ada, E347, "gtk__cell_renderer_E");
   E345 : Short_Integer; pragma Import (Ada, E345, "gtk__cell_layout_E");
   E341 : Short_Integer; pragma Import (Ada, E341, "gtk__cell_area_E");
   E319 : Short_Integer; pragma Import (Ada, E319, "gtk__container_E");
   E391 : Short_Integer; pragma Import (Ada, E391, "gtk__bin_E");
   E315 : Short_Integer; pragma Import (Ada, E315, "gtk__box_E");
   E339 : Short_Integer; pragma Import (Ada, E339, "gtk__entry_completion_E");
   E361 : Short_Integer; pragma Import (Ada, E361, "gtk__misc_E");
   E363 : Short_Integer; pragma Import (Ada, E363, "gtk__notebook_E");
   E381 : Short_Integer; pragma Import (Ada, E381, "gtk__status_bar_E");
   E260 : Short_Integer; pragma Import (Ada, E260, "gtk__style_provider_E");
   E248 : Short_Integer; pragma Import (Ada, E248, "gtk__settings_E");
   E357 : Short_Integer; pragma Import (Ada, E357, "gtk__style_context_E");
   E353 : Short_Integer; pragma Import (Ada, E353, "gtk__icon_set_E");
   E351 : Short_Integer; pragma Import (Ada, E351, "gtk__image_E");
   E325 : Short_Integer; pragma Import (Ada, E325, "gtk__gentry_E");
   E228 : Short_Integer; pragma Import (Ada, E228, "gtk__window_E");
   E246 : Short_Integer; pragma Import (Ada, E246, "gtk__dialog_E");
   E365 : Short_Integer; pragma Import (Ada, E365, "gtk__print_operation_E");
   E230 : Short_Integer; pragma Import (Ada, E230, "gtk__arguments_E");
   E421 : Short_Integer; pragma Import (Ada, E421, "gdk__cairo_E");
   E443 : Short_Integer; pragma Import (Ada, E443, "gdk__device_manager_E");
   E462 : Short_Integer; pragma Import (Ada, E462, "gtk__action_E");
   E466 : Short_Integer; pragma Import (Ada, E466, "gtk__activatable_E");
   E458 : Short_Integer; pragma Import (Ada, E458, "gtk__alignment_E");
   E460 : Short_Integer; pragma Import (Ada, E460, "gtk__button_E");
   E445 : Short_Integer; pragma Import (Ada, E445, "gtk__css_provider_E");
   E423 : Short_Integer; pragma Import (Ada, E423, "gtk__drawing_area_E");
   E472 : Short_Integer; pragma Import (Ada, E472, "gtk__grid_E");
   E447 : Short_Integer; pragma Import (Ada, E447, "gtk__icon_theme_E");
   E486 : Short_Integer; pragma Import (Ada, E486, "gtk__main_E");
   E431 : Short_Integer; pragma Import (Ada, E431, "gtk__marshallers_E");
   E470 : Short_Integer; pragma Import (Ada, E470, "gtk__toggle_button_E");
   E468 : Short_Integer; pragma Import (Ada, E468, "gtk__check_button_E");
   E433 : Short_Integer; pragma Import (Ada, E433, "gtk__tree_view_column_E");
   E449 : Short_Integer; pragma Import (Ada, E449, "pango__cairo_E");
   E435 : Short_Integer; pragma Import (Ada, E435, "gtkada__style_E");
   E474 : Short_Integer; pragma Import (Ada, E474, "pkg_buffergenerico_E");
   E394 : Short_Integer; pragma Import (Ada, E394, "pkg_debug_E");
   E392 : Short_Integer; pragma Import (Ada, E392, "pkg_tipos_E");
   E419 : Short_Integer; pragma Import (Ada, E419, "double_buffer_E");
   E131 : Short_Integer; pragma Import (Ada, E131, "pkg_averias_E");
   E396 : Short_Integer; pragma Import (Ada, E396, "pkg_graficos_E");
   E501 : Short_Integer; pragma Import (Ada, E501, "pkg_cronometro_realtime_E");
   E488 : Short_Integer; pragma Import (Ada, E488, "pkg_semaforo_E");
   E507 : Short_Integer; pragma Import (Ada, E507, "pkg_taxi_E");
   E505 : Short_Integer; pragma Import (Ada, E505, "pkg_peaton_E");
   E490 : Short_Integer; pragma Import (Ada, E490, "pkg_teleoperador_E");
   E106 : Short_Integer; pragma Import (Ada, E106, "pkg_coches_E");
   E509 : Short_Integer; pragma Import (Ada, E509, "pkg_tren_E");

   Sec_Default_Sized_Stacks : array (1 .. 1) of aliased System.Secondary_Stack.SS_Stack (System.Parameters.Runtime_Default_Sec_Stack_Size);

   Local_Priority_Specific_Dispatching : constant String := "";
   Local_Interrupt_States : constant String := "";

   Is_Elaborated : Boolean := False;

   procedure finalize_library is
   begin
      E507 := E507 - 1;
      declare
         procedure F1;
         pragma Import (Ada, F1, "pkg_taxi__finalize_spec");
      begin
         F1;
      end;
      E488 := E488 - 1;
      declare
         procedure F2;
         pragma Import (Ada, F2, "pkg_semaforo__finalize_spec");
      begin
         F2;
      end;
      E396 := E396 - 1;
      declare
         procedure F3;
         pragma Import (Ada, F3, "pkg_graficos__finalize_spec");
      begin
         F3;
      end;
      E131 := E131 - 1;
      declare
         procedure F4;
         pragma Import (Ada, F4, "pkg_averias__finalize_spec");
      begin
         F4;
      end;
      declare
         procedure F5;
         pragma Import (Ada, F5, "double_buffer__finalize_body");
      begin
         E419 := E419 - 1;
         F5;
      end;
      declare
         procedure F6;
         pragma Import (Ada, F6, "double_buffer__finalize_spec");
      begin
         F6;
      end;
      E435 := E435 - 1;
      declare
         procedure F7;
         pragma Import (Ada, F7, "gtkada__style__finalize_spec");
      begin
         F7;
      end;
      E433 := E433 - 1;
      declare
         procedure F8;
         pragma Import (Ada, F8, "gtk__tree_view_column__finalize_spec");
      begin
         F8;
      end;
      E468 := E468 - 1;
      declare
         procedure F9;
         pragma Import (Ada, F9, "gtk__check_button__finalize_spec");
      begin
         F9;
      end;
      E470 := E470 - 1;
      declare
         procedure F10;
         pragma Import (Ada, F10, "gtk__toggle_button__finalize_spec");
      begin
         F10;
      end;
      E447 := E447 - 1;
      declare
         procedure F11;
         pragma Import (Ada, F11, "gtk__icon_theme__finalize_spec");
      begin
         F11;
      end;
      E472 := E472 - 1;
      declare
         procedure F12;
         pragma Import (Ada, F12, "gtk__grid__finalize_spec");
      begin
         F12;
      end;
      E423 := E423 - 1;
      declare
         procedure F13;
         pragma Import (Ada, F13, "gtk__drawing_area__finalize_spec");
      begin
         F13;
      end;
      E445 := E445 - 1;
      declare
         procedure F14;
         pragma Import (Ada, F14, "gtk__css_provider__finalize_spec");
      begin
         F14;
      end;
      E460 := E460 - 1;
      declare
         procedure F15;
         pragma Import (Ada, F15, "gtk__button__finalize_spec");
      begin
         F15;
      end;
      E458 := E458 - 1;
      declare
         procedure F16;
         pragma Import (Ada, F16, "gtk__alignment__finalize_spec");
      begin
         F16;
      end;
      E462 := E462 - 1;
      declare
         procedure F17;
         pragma Import (Ada, F17, "gtk__action__finalize_spec");
      begin
         F17;
      end;
      E443 := E443 - 1;
      declare
         procedure F18;
         pragma Import (Ada, F18, "gdk__device_manager__finalize_spec");
      begin
         F18;
      end;
      E228 := E228 - 1;
      E262 := E262 - 1;
      E349 := E349 - 1;
      E357 := E357 - 1;
      E284 := E284 - 1;
      E381 := E381 - 1;
      E365 := E365 - 1;
      E363 := E363 - 1;
      E325 := E325 - 1;
      E339 := E339 - 1;
      E337 := E337 - 1;
      E246 := E246 - 1;
      E319 := E319 - 1;
      E347 := E347 - 1;
      E341 := E341 - 1;
      E321 := E321 - 1;
      E278 := E278 - 1;
      E270 := E270 - 1;
      E252 := E252 - 1;
      declare
         procedure F19;
         pragma Import (Ada, F19, "gtk__print_operation__finalize_spec");
      begin
         F19;
      end;
      declare
         procedure F20;
         pragma Import (Ada, F20, "gtk__dialog__finalize_spec");
      begin
         F20;
      end;
      declare
         procedure F21;
         pragma Import (Ada, F21, "gtk__window__finalize_spec");
      begin
         F21;
      end;
      declare
         procedure F22;
         pragma Import (Ada, F22, "gtk__gentry__finalize_spec");
      begin
         F22;
      end;
      E351 := E351 - 1;
      declare
         procedure F23;
         pragma Import (Ada, F23, "gtk__image__finalize_spec");
      begin
         F23;
      end;
      E353 := E353 - 1;
      declare
         procedure F24;
         pragma Import (Ada, F24, "gtk__icon_set__finalize_spec");
      begin
         F24;
      end;
      declare
         procedure F25;
         pragma Import (Ada, F25, "gtk__style_context__finalize_spec");
      begin
         F25;
      end;
      E248 := E248 - 1;
      declare
         procedure F26;
         pragma Import (Ada, F26, "gtk__settings__finalize_spec");
      begin
         F26;
      end;
      declare
         procedure F27;
         pragma Import (Ada, F27, "gtk__status_bar__finalize_spec");
      begin
         F27;
      end;
      declare
         procedure F28;
         pragma Import (Ada, F28, "gtk__notebook__finalize_spec");
      begin
         F28;
      end;
      E361 := E361 - 1;
      declare
         procedure F29;
         pragma Import (Ada, F29, "gtk__misc__finalize_spec");
      begin
         F29;
      end;
      declare
         procedure F30;
         pragma Import (Ada, F30, "gtk__entry_completion__finalize_spec");
      begin
         F30;
      end;
      E315 := E315 - 1;
      declare
         procedure F31;
         pragma Import (Ada, F31, "gtk__box__finalize_spec");
      begin
         F31;
      end;
      E391 := E391 - 1;
      declare
         procedure F32;
         pragma Import (Ada, F32, "gtk__bin__finalize_spec");
      begin
         F32;
      end;
      declare
         procedure F33;
         pragma Import (Ada, F33, "gtk__container__finalize_spec");
      begin
         F33;
      end;
      declare
         procedure F34;
         pragma Import (Ada, F34, "gtk__cell_area__finalize_spec");
      begin
         F34;
      end;
      declare
         procedure F35;
         pragma Import (Ada, F35, "gtk__cell_renderer__finalize_spec");
      begin
         F35;
      end;
      declare
         procedure F36;
         pragma Import (Ada, F36, "gtk__widget__finalize_spec");
      begin
         F36;
      end;
      declare
         procedure F37;
         pragma Import (Ada, F37, "gtk__tree_model__finalize_spec");
      begin
         F37;
      end;
      declare
         procedure F38;
         pragma Import (Ada, F38, "gtk__style__finalize_spec");
      begin
         F38;
      end;
      E282 := E282 - 1;
      declare
         procedure F39;
         pragma Import (Ada, F39, "gtk__selection_data__finalize_spec");
      begin
         F39;
      end;
      E355 := E355 - 1;
      declare
         procedure F40;
         pragma Import (Ada, F40, "gtk__icon_source__finalize_spec");
      begin
         F40;
      end;
      declare
         procedure F41;
         pragma Import (Ada, F41, "gtk__entry_buffer__finalize_spec");
      begin
         F41;
      end;
      declare
         procedure F42;
         pragma Import (Ada, F42, "gtk__adjustment__finalize_spec");
      begin
         F42;
      end;
      declare
         procedure F43;
         pragma Import (Ada, F43, "gtk__accel_group__finalize_spec");
      begin
         F43;
      end;
      E268 := E268 - 1;
      declare
         procedure F44;
         pragma Import (Ada, F44, "gdk__drag_contexts__finalize_spec");
      begin
         F44;
      end;
      E266 := E266 - 1;
      declare
         procedure F45;
         pragma Import (Ada, F45, "gdk__device__finalize_spec");
      begin
         F45;
      end;
      E250 := E250 - 1;
      declare
         procedure F46;
         pragma Import (Ada, F46, "gdk__screen__finalize_spec");
      begin
         F46;
      end;
      E274 := E274 - 1;
      declare
         procedure F47;
         pragma Import (Ada, F47, "gdk__pixbuf__finalize_spec");
      begin
         F47;
      end;
      declare
         procedure F48;
         pragma Import (Ada, F48, "gdk__frame_clock__finalize_spec");
      begin
         F48;
      end;
      declare
         procedure F49;
         pragma Import (Ada, F49, "gdk__display__finalize_spec");
      begin
         F49;
      end;
      E373 := E373 - 1;
      declare
         procedure F50;
         pragma Import (Ada, F50, "gtk__print_context__finalize_spec");
      begin
         F50;
      end;
      E309 := E309 - 1;
      declare
         procedure F51;
         pragma Import (Ada, F51, "pango__layout__finalize_spec");
      begin
         F51;
      end;
      E313 := E313 - 1;
      declare
         procedure F52;
         pragma Import (Ada, F52, "pango__tabs__finalize_spec");
      begin
         F52;
      end;
      E375 := E375 - 1;
      declare
         procedure F53;
         pragma Import (Ada, F53, "pango__font_map__finalize_spec");
      begin
         F53;
      end;
      E291 := E291 - 1;
      declare
         procedure F54;
         pragma Import (Ada, F54, "pango__context__finalize_spec");
      begin
         F54;
      end;
      E305 := E305 - 1;
      declare
         procedure F55;
         pragma Import (Ada, F55, "pango__fontset__finalize_spec");
      begin
         F55;
      end;
      E301 := E301 - 1;
      declare
         procedure F56;
         pragma Import (Ada, F56, "pango__font_family__finalize_spec");
      begin
         F56;
      end;
      E303 := E303 - 1;
      declare
         procedure F57;
         pragma Import (Ada, F57, "pango__font_face__finalize_spec");
      begin
         F57;
      end;
      E387 := E387 - 1;
      declare
         procedure F58;
         pragma Import (Ada, F58, "gtk__text_tag__finalize_spec");
      begin
         F58;
      end;
      E295 := E295 - 1;
      declare
         procedure F59;
         pragma Import (Ada, F59, "pango__font__finalize_spec");
      begin
         F59;
      end;
      E299 := E299 - 1;
      declare
         procedure F60;
         pragma Import (Ada, F60, "pango__language__finalize_spec");
      begin
         F60;
      end;
      E297 := E297 - 1;
      declare
         procedure F61;
         pragma Import (Ada, F61, "pango__font_metrics__finalize_spec");
      begin
         F61;
      end;
      E311 := E311 - 1;
      declare
         procedure F62;
         pragma Import (Ada, F62, "pango__attributes__finalize_spec");
      begin
         F62;
      end;
      E286 := E286 - 1;
      declare
         procedure F63;
         pragma Import (Ada, F63, "gtk__target_list__finalize_spec");
      begin
         F63;
      end;
      E379 := E379 - 1;
      declare
         procedure F64;
         pragma Import (Ada, F64, "gtk__print_settings__finalize_spec");
      begin
         F64;
      end;
      E367 := E367 - 1;
      declare
         procedure F65;
         pragma Import (Ada, F65, "gtk__page_setup__finalize_spec");
      begin
         F65;
      end;
      E371 := E371 - 1;
      declare
         procedure F66;
         pragma Import (Ada, F66, "gtk__paper_size__finalize_spec");
      begin
         F66;
      end;
      E359 := E359 - 1;
      declare
         procedure F67;
         pragma Import (Ada, F67, "gtk__css_section__finalize_spec");
      begin
         F67;
      end;
      E343 := E343 - 1;
      declare
         procedure F68;
         pragma Import (Ada, F68, "gtk__cell_area_context__finalize_spec");
      begin
         F68;
      end;
      E280 := E280 - 1;
      declare
         procedure F69;
         pragma Import (Ada, F69, "gtk__builder__finalize_spec");
      begin
         F69;
      end;
      E329 := E329 - 1;
      declare
         procedure F70;
         pragma Import (Ada, F70, "glib__variant__finalize_spec");
      begin
         F70;
      end;
      E204 := E204 - 1;
      declare
         procedure F71;
         pragma Import (Ada, F71, "glib__object__finalize_spec");
      begin
         F71;
      end;
      E272 := E272 - 1;
      declare
         procedure F72;
         pragma Import (Ada, F72, "gdk__frame_timings__finalize_spec");
      begin
         F72;
      end;
      E182 := E182 - 1;
      declare
         procedure F73;
         pragma Import (Ada, F73, "glib__finalize_spec");
      begin
         F73;
      end;
      E163 := E163 - 1;
      declare
         procedure F74;
         pragma Import (Ada, F74, "system__tasking__protected_objects__entries__finalize_spec");
      begin
         F74;
      end;
      E193 := E193 - 1;
      declare
         procedure F75;
         pragma Import (Ada, F75, "system__pool_global__finalize_spec");
      begin
         F75;
      end;
      E115 := E115 - 1;
      declare
         procedure F76;
         pragma Import (Ada, F76, "ada__text_io__finalize_spec");
      begin
         F76;
      end;
      E208 := E208 - 1;
      declare
         procedure F77;
         pragma Import (Ada, F77, "system__storage_pools__subpools__finalize_spec");
      begin
         F77;
      end;
      E189 := E189 - 1;
      declare
         procedure F78;
         pragma Import (Ada, F78, "system__finalization_masters__finalize_spec");
      begin
         F78;
      end;
      declare
         procedure F79;
         pragma Import (Ada, F79, "system__file_io__finalize_body");
      begin
         E125 := E125 - 1;
         F79;
      end;
      declare
         procedure Reraise_Library_Exception_If_Any;
            pragma Import (Ada, Reraise_Library_Exception_If_Any, "__gnat_reraise_library_exception_if_any");
      begin
         Reraise_Library_Exception_If_Any;
      end;
   end finalize_library;

   procedure adafinal is
      procedure s_stalib_adafinal;
      pragma Import (Ada, s_stalib_adafinal, "system__standard_library__adafinal");

      procedure Runtime_Finalize;
      pragma Import (C, Runtime_Finalize, "__gnat_runtime_finalize");

   begin
      if not Is_Elaborated then
         return;
      end if;
      Is_Elaborated := False;
      Runtime_Finalize;
      s_stalib_adafinal;
   end adafinal;

   type No_Param_Proc is access procedure;
   pragma Favor_Top_Level (No_Param_Proc);

   procedure adainit is
      Main_Priority : Integer;
      pragma Import (C, Main_Priority, "__gl_main_priority");
      Time_Slice_Value : Integer;
      pragma Import (C, Time_Slice_Value, "__gl_time_slice_val");
      WC_Encoding : Character;
      pragma Import (C, WC_Encoding, "__gl_wc_encoding");
      Locking_Policy : Character;
      pragma Import (C, Locking_Policy, "__gl_locking_policy");
      Queuing_Policy : Character;
      pragma Import (C, Queuing_Policy, "__gl_queuing_policy");
      Task_Dispatching_Policy : Character;
      pragma Import (C, Task_Dispatching_Policy, "__gl_task_dispatching_policy");
      Priority_Specific_Dispatching : System.Address;
      pragma Import (C, Priority_Specific_Dispatching, "__gl_priority_specific_dispatching");
      Num_Specific_Dispatching : Integer;
      pragma Import (C, Num_Specific_Dispatching, "__gl_num_specific_dispatching");
      Main_CPU : Integer;
      pragma Import (C, Main_CPU, "__gl_main_cpu");
      Interrupt_States : System.Address;
      pragma Import (C, Interrupt_States, "__gl_interrupt_states");
      Num_Interrupt_States : Integer;
      pragma Import (C, Num_Interrupt_States, "__gl_num_interrupt_states");
      Unreserve_All_Interrupts : Integer;
      pragma Import (C, Unreserve_All_Interrupts, "__gl_unreserve_all_interrupts");
      Detect_Blocking : Integer;
      pragma Import (C, Detect_Blocking, "__gl_detect_blocking");
      Default_Stack_Size : Integer;
      pragma Import (C, Default_Stack_Size, "__gl_default_stack_size");
      Default_Secondary_Stack_Size : System.Parameters.Size_Type;
      pragma Import (C, Default_Secondary_Stack_Size, "__gnat_default_ss_size");
      Bind_Env_Addr : System.Address;
      pragma Import (C, Bind_Env_Addr, "__gl_bind_env_addr");

      procedure Runtime_Initialize (Install_Handler : Integer);
      pragma Import (C, Runtime_Initialize, "__gnat_runtime_initialize");

      Finalize_Library_Objects : No_Param_Proc;
      pragma Import (C, Finalize_Library_Objects, "__gnat_finalize_library_objects");
      Binder_Sec_Stacks_Count : Natural;
      pragma Import (Ada, Binder_Sec_Stacks_Count, "__gnat_binder_ss_count");
      Default_Sized_SS_Pool : System.Address;
      pragma Import (Ada, Default_Sized_SS_Pool, "__gnat_default_ss_pool");

   begin
      if Is_Elaborated then
         return;
      end if;
      Is_Elaborated := True;
      Main_Priority := -1;
      Time_Slice_Value := -1;
      WC_Encoding := 'b';
      Locking_Policy := ' ';
      Queuing_Policy := ' ';
      Task_Dispatching_Policy := ' ';
      System.Restrictions.Run_Time_Restrictions :=
        (Set =>
          (False, False, False, False, False, False, False, False, 
           False, False, False, False, False, False, False, False, 
           False, False, False, False, False, False, False, False, 
           False, False, False, False, False, False, False, False, 
           False, False, False, False, False, False, False, False, 
           False, False, False, False, False, False, False, False, 
           False, False, False, False, False, False, False, False, 
           False, False, False, False, False, False, False, False, 
           False, False, False, False, False, False, False, False, 
           False, False, False, False, False, False, False, False, 
           True, False, False, False, False, False, False, False, 
           False, False, False, False, False, False, False, False, 
           False),
         Value => (0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
         Violated =>
          (True, True, False, False, True, True, False, False, 
           True, False, False, True, True, True, True, False, 
           False, False, False, True, True, False, True, True, 
           False, True, True, False, True, True, True, True, 
           False, True, False, False, False, True, False, False, 
           True, False, True, False, True, True, False, True, 
           False, True, True, False, True, True, False, False, 
           True, False, False, False, False, False, True, False, 
           True, True, True, False, False, True, False, True, 
           True, True, False, True, True, False, True, True, 
           True, True, False, False, False, False, False, False, 
           False, False, True, True, True, True, True, True, 
           False),
         Count => (0, 0, 0, 2, 2, 2, 7, 1, 6, 0),
         Unknown => (False, False, False, False, False, False, True, True, True, False));
      Priority_Specific_Dispatching :=
        Local_Priority_Specific_Dispatching'Address;
      Num_Specific_Dispatching := 0;
      Main_CPU := -1;
      Interrupt_States := Local_Interrupt_States'Address;
      Num_Interrupt_States := 0;
      Unreserve_All_Interrupts := 0;
      Detect_Blocking := 0;
      Default_Stack_Size := -1;

      ada_main'Elab_Body;
      Default_Secondary_Stack_Size := System.Parameters.Runtime_Default_Sec_Stack_Size;
      Binder_Sec_Stacks_Count := 1;
      Default_Sized_SS_Pool := Sec_Default_Sized_Stacks'Address;

      Runtime_Initialize (1);

      Finalize_Library_Objects := finalize_library'access;

      Ada.Exceptions'Elab_Spec;
      System.Soft_Links'Elab_Spec;
      System.Exception_Table'Elab_Body;
      E010 := E010 + 1;
      Ada.Containers'Elab_Spec;
      E033 := E033 + 1;
      Ada.Io_Exceptions'Elab_Spec;
      E063 := E063 + 1;
      Ada.Strings'Elab_Spec;
      E007 := E007 + 1;
      Ada.Strings.Maps'Elab_Spec;
      E051 := E051 + 1;
      Ada.Strings.Maps.Constants'Elab_Spec;
      E055 := E055 + 1;
      Interfaces.C'Elab_Spec;
      E038 := E038 + 1;
      System.Exceptions'Elab_Spec;
      E019 := E019 + 1;
      System.Object_Reader'Elab_Spec;
      E074 := E074 + 1;
      System.Dwarf_Lines'Elab_Spec;
      E045 := E045 + 1;
      System.Os_Lib'Elab_Body;
      E068 := E068 + 1;
      System.Soft_Links.Initialize'Elab_Body;
      E090 := E090 + 1;
      E012 := E012 + 1;
      System.Traceback.Symbolic'Elab_Body;
      E032 := E032 + 1;
      E016 := E016 + 1;
      Ada.Assertions'Elab_Spec;
      E429 := E429 + 1;
      Ada.Numerics'Elab_Spec;
      E113 := E113 + 1;
      Ada.Strings.Utf_Encoding'Elab_Spec;
      E094 := E094 + 1;
      Ada.Tags'Elab_Spec;
      Ada.Tags'Elab_Body;
      E100 := E100 + 1;
      Ada.Strings.Text_Buffers'Elab_Spec;
      Ada.Strings.Text_Buffers'Elab_Body;
      E005 := E005 + 1;
      Gnat'Elab_Spec;
      E213 := E213 + 1;
      Interfaces.C.Strings'Elab_Spec;
      E187 := E187 + 1;
      Ada.Streams'Elab_Spec;
      E117 := E117 + 1;
      System.File_Control_Block'Elab_Spec;
      E129 := E129 + 1;
      System.Finalization_Root'Elab_Spec;
      System.Finalization_Root'Elab_Body;
      E128 := E128 + 1;
      Ada.Finalization'Elab_Spec;
      E126 := E126 + 1;
      System.File_Io'Elab_Body;
      E125 := E125 + 1;
      System.Storage_Pools'Elab_Spec;
      E191 := E191 + 1;
      System.Finalization_Masters'Elab_Spec;
      System.Finalization_Masters'Elab_Body;
      E189 := E189 + 1;
      System.Storage_Pools.Subpools'Elab_Spec;
      System.Storage_Pools.Subpools'Elab_Body;
      E208 := E208 + 1;
      System.Task_Info'Elab_Spec;
      E150 := E150 + 1;
      System.Task_Primitives.Operations'Elab_Body;
      E144 := E144 + 1;
      Ada.Calendar'Elab_Spec;
      Ada.Calendar'Elab_Body;
      E110 := E110 + 1;
      Ada.Calendar.Delays'Elab_Body;
      E108 := E108 + 1;
      Ada.Real_Time'Elab_Spec;
      Ada.Real_Time'Elab_Body;
      E494 := E494 + 1;
      Ada.Text_Io'Elab_Spec;
      Ada.Text_Io'Elab_Body;
      E115 := E115 + 1;
      System.Pool_Global'Elab_Spec;
      System.Pool_Global'Elab_Body;
      E193 := E193 + 1;
      System.Random_Seed'Elab_Body;
      E498 := E498 + 1;
      System.Tasking.Initialization'Elab_Body;
      E167 := E167 + 1;
      System.Tasking.Protected_Objects'Elab_Body;
      E133 := E133 + 1;
      System.Tasking.Protected_Objects.Entries'Elab_Spec;
      E163 := E163 + 1;
      System.Tasking.Queuing'Elab_Body;
      E175 := E175 + 1;
      System.Tasking.Stages'Elab_Body;
      E492 := E492 + 1;
      Glib'Elab_Spec;
      Gtkada.Types'Elab_Spec;
      E185 := E185 + 1;
      E182 := E182 + 1;
      Gdk.Frame_Timings'Elab_Spec;
      Gdk.Frame_Timings'Elab_Body;
      E272 := E272 + 1;
      E224 := E224 + 1;
      Gdk.Visual'Elab_Body;
      E254 := E254 + 1;
      E226 := E226 + 1;
      E453 := E453 + 1;
      E218 := E218 + 1;
      Glib.Object'Elab_Spec;
      E206 := E206 + 1;
      Glib.Values'Elab_Body;
      E222 := E222 + 1;
      E212 := E212 + 1;
      Glib.Object'Elab_Body;
      E204 := E204 + 1;
      E220 := E220 + 1;
      E232 := E232 + 1;
      E234 := E234 + 1;
      E241 := E241 + 1;
      Glib.Generic_Properties'Elab_Spec;
      Glib.Generic_Properties'Elab_Body;
      E239 := E239 + 1;
      Gdk.Color'Elab_Spec;
      E264 := E264 + 1;
      E244 := E244 + 1;
      E237 := E237 + 1;
      E369 := E369 + 1;
      E256 := E256 + 1;
      E455 := E455 + 1;
      E451 := E451 + 1;
      E331 := E331 + 1;
      Glib.Variant'Elab_Spec;
      Glib.Variant'Elab_Body;
      E329 := E329 + 1;
      E327 := E327 + 1;
      Gtk.Actionable'Elab_Spec;
      E464 := E464 + 1;
      Gtk.Builder'Elab_Spec;
      Gtk.Builder'Elab_Body;
      E280 := E280 + 1;
      E317 := E317 + 1;
      Gtk.Cell_Area_Context'Elab_Spec;
      Gtk.Cell_Area_Context'Elab_Body;
      E343 := E343 + 1;
      Gtk.Css_Section'Elab_Spec;
      Gtk.Css_Section'Elab_Body;
      E359 := E359 + 1;
      E258 := E258 + 1;
      Gtk.Orientable'Elab_Spec;
      E323 := E323 + 1;
      Gtk.Paper_Size'Elab_Spec;
      Gtk.Paper_Size'Elab_Body;
      E371 := E371 + 1;
      Gtk.Page_Setup'Elab_Spec;
      Gtk.Page_Setup'Elab_Body;
      E367 := E367 + 1;
      Gtk.Print_Settings'Elab_Spec;
      Gtk.Print_Settings'Elab_Body;
      E379 := E379 + 1;
      E288 := E288 + 1;
      Gtk.Target_List'Elab_Spec;
      Gtk.Target_List'Elab_Body;
      E286 := E286 + 1;
      E293 := E293 + 1;
      Pango.Attributes'Elab_Spec;
      Pango.Attributes'Elab_Body;
      E311 := E311 + 1;
      Pango.Font_Metrics'Elab_Spec;
      Pango.Font_Metrics'Elab_Body;
      E297 := E297 + 1;
      Pango.Language'Elab_Spec;
      Pango.Language'Elab_Body;
      E299 := E299 + 1;
      Pango.Font'Elab_Spec;
      Pango.Font'Elab_Body;
      E295 := E295 + 1;
      E385 := E385 + 1;
      Gtk.Text_Tag'Elab_Spec;
      Gtk.Text_Tag'Elab_Body;
      E387 := E387 + 1;
      Pango.Font_Face'Elab_Spec;
      Pango.Font_Face'Elab_Body;
      E303 := E303 + 1;
      Pango.Font_Family'Elab_Spec;
      Pango.Font_Family'Elab_Body;
      E301 := E301 + 1;
      Pango.Fontset'Elab_Spec;
      Pango.Fontset'Elab_Body;
      E305 := E305 + 1;
      E307 := E307 + 1;
      Pango.Context'Elab_Spec;
      Pango.Context'Elab_Body;
      E291 := E291 + 1;
      Pango.Font_Map'Elab_Spec;
      Pango.Font_Map'Elab_Body;
      E375 := E375 + 1;
      Pango.Tabs'Elab_Spec;
      Pango.Tabs'Elab_Body;
      E313 := E313 + 1;
      Pango.Layout'Elab_Spec;
      Pango.Layout'Elab_Body;
      E309 := E309 + 1;
      Gtk.Print_Context'Elab_Spec;
      Gtk.Print_Context'Elab_Body;
      E373 := E373 + 1;
      Gdk.Display'Elab_Spec;
      Gdk.Frame_Clock'Elab_Spec;
      Gdk.Pixbuf'Elab_Spec;
      E274 := E274 + 1;
      Gdk.Screen'Elab_Spec;
      Gdk.Screen'Elab_Body;
      E250 := E250 + 1;
      Gdk.Device'Elab_Spec;
      Gdk.Device'Elab_Body;
      E266 := E266 + 1;
      Gdk.Drag_Contexts'Elab_Spec;
      Gdk.Drag_Contexts'Elab_Body;
      E268 := E268 + 1;
      Gdk.Window'Elab_Spec;
      E389 := E389 + 1;
      Gtk.Accel_Group'Elab_Spec;
      Gtk.Adjustment'Elab_Spec;
      Gtk.Cell_Editable'Elab_Spec;
      Gtk.Entry_Buffer'Elab_Spec;
      Gtk.Icon_Source'Elab_Spec;
      Gtk.Icon_Source'Elab_Body;
      E355 := E355 + 1;
      Gtk.Selection_Data'Elab_Spec;
      Gtk.Selection_Data'Elab_Body;
      E282 := E282 + 1;
      Gtk.Style'Elab_Spec;
      E383 := E383 + 1;
      Gtk.Tree_Model'Elab_Spec;
      Gtk.Widget'Elab_Spec;
      Gtk.Cell_Renderer'Elab_Spec;
      E345 := E345 + 1;
      Gtk.Cell_Area'Elab_Spec;
      Gtk.Container'Elab_Spec;
      Gtk.Bin'Elab_Spec;
      Gtk.Bin'Elab_Body;
      E391 := E391 + 1;
      Gtk.Box'Elab_Spec;
      Gtk.Box'Elab_Body;
      E315 := E315 + 1;
      Gtk.Entry_Completion'Elab_Spec;
      Gtk.Misc'Elab_Spec;
      Gtk.Misc'Elab_Body;
      E361 := E361 + 1;
      Gtk.Notebook'Elab_Spec;
      Gtk.Status_Bar'Elab_Spec;
      E260 := E260 + 1;
      Gtk.Settings'Elab_Spec;
      Gtk.Settings'Elab_Body;
      E248 := E248 + 1;
      Gtk.Style_Context'Elab_Spec;
      Gtk.Icon_Set'Elab_Spec;
      Gtk.Icon_Set'Elab_Body;
      E353 := E353 + 1;
      Gtk.Image'Elab_Spec;
      Gtk.Image'Elab_Body;
      E351 := E351 + 1;
      Gtk.Gentry'Elab_Spec;
      Gtk.Window'Elab_Spec;
      Gtk.Dialog'Elab_Spec;
      Gtk.Print_Operation'Elab_Spec;
      E230 := E230 + 1;
      Gdk.Display'Elab_Body;
      E252 := E252 + 1;
      Gdk.Frame_Clock'Elab_Body;
      E270 := E270 + 1;
      Gtk.Accel_Group'Elab_Body;
      E278 := E278 + 1;
      Gtk.Adjustment'Elab_Body;
      E321 := E321 + 1;
      Gtk.Cell_Area'Elab_Body;
      E341 := E341 + 1;
      E333 := E333 + 1;
      Gtk.Cell_Renderer'Elab_Body;
      E347 := E347 + 1;
      Gtk.Container'Elab_Body;
      E319 := E319 + 1;
      Gtk.Dialog'Elab_Body;
      E246 := E246 + 1;
      E335 := E335 + 1;
      Gtk.Entry_Buffer'Elab_Body;
      E337 := E337 + 1;
      Gtk.Entry_Completion'Elab_Body;
      E339 := E339 + 1;
      Gtk.Gentry'Elab_Body;
      E325 := E325 + 1;
      Gtk.Notebook'Elab_Body;
      E363 := E363 + 1;
      Gtk.Print_Operation'Elab_Body;
      E365 := E365 + 1;
      E377 := E377 + 1;
      Gtk.Status_Bar'Elab_Body;
      E381 := E381 + 1;
      Gtk.Style'Elab_Body;
      E284 := E284 + 1;
      Gtk.Style_Context'Elab_Body;
      E357 := E357 + 1;
      Gtk.Tree_Model'Elab_Body;
      E349 := E349 + 1;
      Gtk.Widget'Elab_Body;
      E262 := E262 + 1;
      Gtk.Window'Elab_Body;
      E228 := E228 + 1;
      E421 := E421 + 1;
      Gdk.Device_Manager'Elab_Spec;
      Gdk.Device_Manager'Elab_Body;
      E443 := E443 + 1;
      Gtk.Action'Elab_Spec;
      Gtk.Action'Elab_Body;
      E462 := E462 + 1;
      Gtk.Activatable'Elab_Spec;
      E466 := E466 + 1;
      Gtk.Alignment'Elab_Spec;
      Gtk.Alignment'Elab_Body;
      E458 := E458 + 1;
      Gtk.Button'Elab_Spec;
      Gtk.Button'Elab_Body;
      E460 := E460 + 1;
      Gtk.Css_Provider'Elab_Spec;
      Gtk.Css_Provider'Elab_Body;
      E445 := E445 + 1;
      Gtk.Drawing_Area'Elab_Spec;
      Gtk.Drawing_Area'Elab_Body;
      E423 := E423 + 1;
      Gtk.Grid'Elab_Spec;
      Gtk.Grid'Elab_Body;
      E472 := E472 + 1;
      Gtk.Icon_Theme'Elab_Spec;
      Gtk.Icon_Theme'Elab_Body;
      E447 := E447 + 1;
      E486 := E486 + 1;
      E431 := E431 + 1;
      Gtk.Toggle_Button'Elab_Spec;
      Gtk.Toggle_Button'Elab_Body;
      E470 := E470 + 1;
      Gtk.Check_Button'Elab_Spec;
      Gtk.Check_Button'Elab_Body;
      E468 := E468 + 1;
      Gtk.Tree_View_Column'Elab_Spec;
      Gtk.Tree_View_Column'Elab_Body;
      E433 := E433 + 1;
      E449 := E449 + 1;
      Gtkada.Style'Elab_Spec;
      Gtkada.Style'Elab_Body;
      E435 := E435 + 1;
      E474 := E474 + 1;
      Pkg_Debug'Elab_Body;
      E394 := E394 + 1;
      Pkg_Tipos'Elab_Spec;
      E392 := E392 + 1;
      Double_Buffer'Elab_Spec;
      Double_Buffer'Elab_Body;
      E419 := E419 + 1;
      Pkg_Averias'Elab_Spec;
      E131 := E131 + 1;
      Pkg_Graficos'Elab_Spec;
      Pkg_Graficos'Elab_Body;
      E396 := E396 + 1;
      pkg_cronometro_realtime'elab_spec;
      pkg_cronometro_realtime'elab_body;
      E501 := E501 + 1;
      Pkg_Semaforo'Elab_Spec;
      E488 := E488 + 1;
      pkg_taxi'elab_spec;
      pkg_taxi'elab_body;
      E507 := E507 + 1;
      Pkg_Peaton'Elab_Spec;
      Pkg_Peaton'Elab_Body;
      E505 := E505 + 1;
      pkg_teleoperador'elab_spec;
      pkg_teleoperador'elab_body;
      E490 := E490 + 1;
      pkg_coches'elab_spec;
      pkg_coches'elab_body;
      E106 := E106 + 1;
      pkg_tren'elab_spec;
      pkg_tren'elab_body;
      E509 := E509 + 1;
   end adainit;

   procedure Ada_Main_Program;
   pragma Import (Ada, Ada_Main_Program, "_ada_main");

   function main
     (argc : Integer;
      argv : System.Address;
      envp : System.Address)
      return Integer
   is
      procedure Initialize (Addr : System.Address);
      pragma Import (C, Initialize, "__gnat_initialize");

      procedure Finalize;
      pragma Import (C, Finalize, "__gnat_finalize");
      SEH : aliased array (1 .. 2) of Integer;

      Ensure_Reference : aliased System.Address := Ada_Main_Program_Name'Address;
      pragma Volatile (Ensure_Reference);

   begin
      if gnat_argc = 0 then
         gnat_argc := argc;
         gnat_argv := argv;
      end if;
      gnat_envp := envp;

      Initialize (SEH'Address);
      adainit;
      Ada_Main_Program;
      adafinal;
      Finalize;
      return (gnat_exit_status);
   end;

--  BEGIN Object file/option list
   --   /home/str/Escritorio/str-2021-murcia-gomez-francisco-joaquin/practica5/obj/pkg_buffergenerico.o
   --   /home/str/Escritorio/str-2021-murcia-gomez-francisco-joaquin/practica5/obj/pkg_debug.o
   --   /home/str/Escritorio/str-2021-murcia-gomez-francisco-joaquin/practica5/obj/pkg_tipos.o
   --   /home/str/Escritorio/str-2021-murcia-gomez-francisco-joaquin/practica5/obj/double_buffer.o
   --   /home/str/Escritorio/str-2021-murcia-gomez-francisco-joaquin/practica5/obj/pkg_averias.o
   --   /home/str/Escritorio/str-2021-murcia-gomez-francisco-joaquin/practica5/obj/pkg_graficos.o
   --   /home/str/Escritorio/str-2021-murcia-gomez-francisco-joaquin/practica5/obj/pkg_cronometro_realtime.o
   --   /home/str/Escritorio/str-2021-murcia-gomez-francisco-joaquin/practica5/obj/pkg_semaforo.o
   --   /home/str/Escritorio/str-2021-murcia-gomez-francisco-joaquin/practica5/obj/pkg_taxi.o
   --   /home/str/Escritorio/str-2021-murcia-gomez-francisco-joaquin/practica5/obj/pkg_peaton.o
   --   /home/str/Escritorio/str-2021-murcia-gomez-francisco-joaquin/practica5/obj/pkg_teleoperador.o
   --   /home/str/Escritorio/str-2021-murcia-gomez-francisco-joaquin/practica5/obj/pkg_coches.o
   --   /home/str/Escritorio/str-2021-murcia-gomez-francisco-joaquin/practica5/obj/pkg_tren.o
   --   /home/str/Escritorio/str-2021-murcia-gomez-francisco-joaquin/practica5/obj/main.o
   --   -L/home/str/Escritorio/str-2021-murcia-gomez-francisco-joaquin/practica5/obj/
   --   -L/home/str/Escritorio/str-2021-murcia-gomez-francisco-joaquin/practica5/obj/
   --   -L/usr/gnat/lib/gtkada/gtkada.static/gtkada/
   --   -L/usr/gnat/lib/gcc/x86_64-pc-linux-gnu/10.3.1/adalib/
   --   -static
   --   -shared-libgcc
   --   -shared-libgcc
   --   -shared-libgcc
   --   -lgnarl
   --   -lgnat
   --   -lrt
   --   -lpthread
   --   -lm
   --   -ldl
--  END Object file/option list   

end ada_main;
