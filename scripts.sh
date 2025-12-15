#!/bin/bash

# ═══════════════════════════════════════════════════════════
#        CREDIYA S.A.S. - SCRIPTS DE EJECUCIÓN
# ═══════════════════════════════════════════════════════════

clear

echo "╔═══════════════════════════════════════════════════════════╗"
echo "║                                                           ║"
echo "║             🏦  CREDIYA S.A.S.  🏦                        ║"
echo "║                                                           ║"
echo "║              Scripts de Ejecución                         ║"
echo "║                                                           ║"
echo "╚═══════════════════════════════════════════════════════════╝"
echo ""
echo "Seleccione una opción:"
echo ""
echo "  ╔══════════════════════════════════════════════════════╗"
echo "  ║              DOCKER Y BASE DE DATOS                 ║"
echo "  ╠══════════════════════════════════════════════════════╣"
echo "  ║  1. 🐳 Iniciar Docker (MySQL + phpMyAdmin)          ║"
echo "  ║  2. 🛑 Detener Docker                               ║"
echo "  ║  3. 🔄 Reiniciar Base de Datos (borra datos)        ║"
echo "  ║  4. 📋 Ver logs de MySQL                            ║"
echo "  ║  5. 🔍 Ver estado de contenedores                   ║"
echo "  ╚══════════════════════════════════════════════════════╝"
echo ""
echo "  ╔══════════════════════════════════════════════════════╗"
echo "  ║              COMPILACIÓN Y EJECUCIÓN                ║"
echo "  ╠══════════════════════════════════════════════════════╣"
echo "  ║  6. 🔨 Compilar proyecto                            ║"
echo "  ║  7. 🚀 Ejecutar Menú Principal                      ║"
echo "  ║  8. 🧪 Ejecutar Pruebas de Integración              ║"
echo "  ║  9. ✅ Ejecutar Pruebas de Validador                ║"
echo "  ║  10. 📊 Ejecutar Main (demo antigua)                ║"
echo "  ╚══════════════════════════════════════════════════════╝"
echo ""
echo "  ╔══════════════════════════════════════════════════════╗"
echo "  ║              UTILIDADES                             ║"
echo "  ╠══════════════════════════════════════════════════════╣"
echo "  ║  11. 🌐 Abrir phpMyAdmin (navegador)                ║"
echo "  ║  12. 📦 Limpiar proyecto (clean)                    ║"
echo "  ║  13. 🔧 Setup completo (Docker + Compilar)          ║"
echo "  ║  14. ℹ️  Ver información del proyecto                ║"
echo "  ╚══════════════════════════════════════════════════════╝"
echo ""
echo "  ╔══════════════════════════════════════════════════════╗"
echo "  ║  0. ❌ Salir                                         ║"
echo "  ╚══════════════════════════════════════════════════════╝"
echo ""
read -p "Opción: " opcion

case $opcion in
    1)
        echo ""
        echo "╔═══════════════════════════════════════╗"
        echo "║  Iniciando Docker...                 ║"
        echo "╚═══════════════════════════════════════╝"
        echo ""
        docker compose up -d
        echo ""
        echo "✓ Docker iniciado correctamente"
        echo ""
        echo "Servicios disponibles:"
        echo "  • MySQL:      localhost:3306"
        echo "  • phpMyAdmin: http://localhost:8080"
        echo ""
        echo "Credenciales:"
        echo "  Usuario:     root"
        echo "  Contraseña:  crediya123"
        echo "  Base de datos: crediya_db"
        echo ""
        read -p "Presione Enter para continuar..."
        ;;
    
    2)
        echo ""
        echo "╔═══════════════════════════════════════╗"
        echo "║  Deteniendo Docker...                ║"
        echo "╚═══════════════════════════════════════╝"
        echo ""
        docker compose down
        echo ""
        echo "✓ Docker detenido correctamente"
        echo ""
        read -p "Presione Enter para continuar..."
        ;;
    
    3)
        echo ""
        echo "╔═══════════════════════════════════════╗"
        echo "║  ⚠️  REINICIAR BASE DE DATOS          ║"
        echo "╚═══════════════════════════════════════╝"
        echo ""
        echo "⚠️  ADVERTENCIA: Se borrarán TODOS los datos"
        echo ""
        read -p "¿Está seguro? (S/N): " confirmar
        
        if [ "$confirmar" = "S" ] || [ "$confirmar" = "s" ]; then
            echo ""
            echo "→ Deteniendo contenedores y eliminando volúmenes..."
            docker compose down -v
            echo ""
            echo "→ Esperando 3 segundos..."
            sleep 3
            echo ""
            echo "→ Iniciando contenedores con base de datos limpia..."
            docker compose up -d
            echo ""
            echo "→ Esperando que MySQL se inicie (15 segundos)..."
            sleep 15
            echo ""
            echo "✓ Base de datos reiniciada correctamente"
            echo "✓ Los datos de prueba se crearon automáticamente"
        else
            echo ""
            echo "✗ Operación cancelada"
        fi
        echo ""
        read -p "Presione Enter para continuar..."
        ;;
    
    4)
        echo ""
        echo "╔═══════════════════════════════════════╗"
        echo "║  Logs de MySQL                       ║"
        echo "╚═══════════════════════════════════════╝"
        echo ""
        docker logs crediya_mysql
        echo ""
        read -p "Presione Enter para continuar..."
        ;;
    
    5)
        echo ""
        echo "╔═══════════════════════════════════════╗"
        echo "║  Estado de Contenedores              ║"
        echo "╚═══════════════════════════════════════╝"
        echo ""
        docker ps
        echo ""
        read -p "Presione Enter para continuar..."
        ;;
    
    6)
        echo ""
        echo "╔═══════════════════════════════════════╗"
        echo "║  Compilando proyecto...              ║"
        echo "╚═══════════════════════════════════════╝"
        echo ""
        mvn clean compile
        echo ""
        read -p "Presione Enter para continuar..."
        ;;
    
    7)
        echo ""
        echo "╔═══════════════════════════════════════╗"
        echo "║  Ejecutando Menú Principal...        ║"
        echo "╚═══════════════════════════════════════╝"
        echo ""
        mvn exec:java -Dexec.mainClass="com.eduar.MenuPrincipal"
        ;;
    
    8)
        echo ""
        echo "╔═══════════════════════════════════════╗"
        echo "║  Ejecutando Pruebas de Integración...║"
        echo "╚═══════════════════════════════════════╝"
        echo ""
        mvn exec:java -Dexec.mainClass="com.eduar.TestIntegracion"
        echo ""
        read -p "Presione Enter para continuar..."
        ;;
    
    9)
        echo ""
        echo "╔═══════════════════════════════════════╗"
        echo "║  Ejecutando Pruebas de Validador...  ║"
        echo "╚═══════════════════════════════════════╝"
        echo ""
        mvn exec:java -Dexec.mainClass="com.eduar.TestValidador"
        echo ""
        read -p "Presione Enter para continuar..."
        ;;
    
    10)
        echo ""
        echo "╔═══════════════════════════════════════╗"
        echo "║  Ejecutando Main (demo antigua)...   ║"
        echo "╚═══════════════════════════════════════╝"
        echo ""
        mvn exec:java -Dexec.mainClass="com.eduar.Main"
        echo ""
        read -p "Presione Enter para continuar..."
        ;;
    
    11)
        echo ""
        echo "╔═══════════════════════════════════════╗"
        echo "║  Abriendo phpMyAdmin...              ║"
        echo "╚═══════════════════════════════════════╝"
        echo ""
        
        # Detectar sistema operativo y abrir navegador
        if [[ "$OSTYPE" == "linux-gnu"* ]]; then
            xdg-open http://localhost:8080 2>/dev/null || echo "Abra manualmente: http://localhost:8080"
        elif [[ "$OSTYPE" == "darwin"* ]]; then
            open http://localhost:8080
        elif [[ "$OSTYPE" == "msys" ]] || [[ "$OSTYPE" == "cygwin" ]]; then
            start http://localhost:8080
        else
            echo "Sistema operativo no reconocido"
            echo "Abra manualmente en su navegador: http://localhost:8080"
        fi
        
        echo ""
        echo "✓ URL: http://localhost:8080"
        echo ""
        echo "Credenciales:"
        echo "  Usuario:     root"
        echo "  Contraseña:  crediya123"
        echo ""
        read -p "Presione Enter para continuar..."
        ;;
    
    12)
        echo ""
        echo "╔═══════════════════════════════════════╗"
        echo "║  Limpiando proyecto...               ║"
        echo "╚═══════════════════════════════════════╝"
        echo ""
        mvn clean
        echo ""
        echo "✓ Proyecto limpiado"
        echo ""
        read -p "Presione Enter para continuar..."
        ;;
    
    13)
        echo ""
        echo "╔═══════════════════════════════════════╗"
        echo "║  Setup Completo                      ║"
        echo "╚═══════════════════════════════════════╝"
        echo ""
        echo "→ Paso 1/4: Iniciando Docker..."
        docker compose up -d
        echo ""
        echo "→ Paso 2/4: Esperando que MySQL inicie (15 segundos)..."
        sleep 15
        echo ""
        echo "→ Paso 3/4: Limpiando proyecto..."
        mvn clean
        echo ""
        echo "→ Paso 4/4: Compilando proyecto..."
        mvn compile
        echo ""
        echo "╔═══════════════════════════════════════╗"
        echo "║  ✓ Setup completado exitosamente     ║"
        echo "╚═══════════════════════════════════════╝"
        echo ""
        echo "Ya puede ejecutar:"
        echo "  • Menú Principal (opción 7)"
        echo "  • Pruebas de Integración (opción 8)"
        echo ""
        read -p "Presione Enter para continuar..."
        ;;
    
    14)
        echo ""
        echo "╔═══════════════════════════════════════════════════════════╗"
        echo "║              CREDIYA S.A.S. - INFORMACIÓN                ║"
        echo "╠═══════════════════════════════════════════════════════════╣"
        echo "║                                                           ║"
        echo "║  Proyecto:  Sistema de Gestión de Préstamos              ║"
        echo "║  Versión:   1.0                                           ║"
        echo "║  Autor:     Eduar Humberto Guerrero Vergel                ║"
        echo "║                                                           ║"
        echo "╠═══════════════════════════════════════════════════════════╣"
        echo "║  TECNOLOGÍAS                                              ║"
        echo "╠═══════════════════════════════════════════════════════════╣"
        echo "║  • Java 17                                                ║"
        echo "║  • Maven 3.8+                                             ║"
        echo "║  • MySQL 8.0                                              ║"
        echo "║  • Docker                                                 ║"
        echo "║  • JDBC                                                   ║"
        echo "║                                                           ║"
        echo "╠═══════════════════════════════════════════════════════════╣"
        echo "║  MILESTONES COMPLETADOS                                   ║"
        echo "╠═══════════════════════════════════════════════════════════╣"
        echo "║  ✓ Milestone 1: Configuración Maven + Git                ║"
        echo "║  ✓ Milestone 2: Modelo POO                               ║"
        echo "║  ✓ Milestone 3: Persistencia en Archivos                 ║"
        echo "║  ✓ Milestone 4: JDBC + MySQL + Docker                    ║"
        echo "║  ✓ Milestone 5: Servicios + Excepciones                  ║"
        echo "║  ✓ Milestone 6: Menú e Integración                       ║"
        echo "║                                                           ║"
        echo "║  Total: 25 Issues completados                             ║"
        echo "║                                                           ║"
        echo "╚═══════════════════════════════════════════════════════════╝"
        echo ""
        read -p "Presione Enter para continuar..."
        ;;
    
    0)
        echo ""
        echo "╔═══════════════════════════════════════╗"
        echo "║  Gracias por usar CrediYa S.A.S.     ║"
        echo "║  ¡Hasta pronto!                      ║"
        echo "╚═══════════════════════════════════════╝"
        echo ""
        exit 0
        ;;
    
    *)
        echo ""
        echo "✗ Opción inválida"
        echo ""
        read -p "Presione Enter para continuar..."
        ;;
esac
# Volver a ejecutar el script
exec bash "$0"